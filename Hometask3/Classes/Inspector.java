package org.example;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;


// рабочая лошадка этой программы с логикой
// парсинга номеров регулярками
// один инспектор на один запрос
public class Inspector {

    // "грязыный" номер, полученный из ввода
    private final String plateNumber;
    // контекст это содержания json конфига в виде джавового объекта
    private final Context context;
    // словарь латинские символы на кирилицу

    public Inspector(String plateNumber, Context context) {
        this.plateNumber = plateNumber;
        this.context = context;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public Context getContext() {
        return context;
    }


    // чистим номер от незначительных символов типа пробеллов
    private String removeInsignificantSymbols(String plate) {
       return plate.toUpperCase()
                .trim()
                .replaceAll("\\s+", "")
                .replaceAll("RUS", "");
    }

    // получает каноничное представление знака без пробелов и всего прочего
    public String getCanonicalPlateNumberView(String plateNumber) {
        plateNumber = removeInsignificantSymbols(plateNumber);
        return plateNumber;
    }

    // тут что-то вроде паттерна chain of responsibility
    // в функциональном стиле
    // чтобы можно было просто дописывать новые фильтры
    // например если поменяется законодательство или родина прирастёт новыми регионами
    public List<String> inspect() {
        List<String> findingsList = new ArrayList<>();
        String plateNumber = getPlateNumber();

        // получаем каноничную последовательность = только значашие символы
        String canonicalView = getCanonicalPlateNumberView(plateNumber);
        Filters filters = new Filters();

        // прогоняем помер через фильтры
        // если всё ок - сразу выходим
        boolean isValidPlateNumber = filters.LENGTH_FILTER
                .and(filters.USES_ONLY_ALLOWED_LETTERS)
                .and(filters.HAS_REGION_CODE)
                .and(filters.VALIDATE_REGION_CODE)
                .and(filters.VALIDATE_FORMAT)
                .test(canonicalView);

        // если всё ок - сразу выходим и уносим с собой полезную инфу
        // регион регистрации и тип знака по ГОСТу
        if (isValidPlateNumber) {
            Region region = filters.GET_REGION.apply(canonicalView);
            PlateType platetype = filters.GET_PLATE_TYPE.apply(canonicalView);
            findingsList.add("Статус: УСПЕХ\n");
            findingsList.add("Дополнительная информация по номеру:");
            findingsList.add(region.toString());
            findingsList.add(platetype.toString());

            return findingsList;
        }

        // если не ок, то выясняем на каком именно фильтре номер погорел

        // проверяем длину номера
        if (filters.LENGTH_FILTER.negate().test(canonicalView)) {
            findingsList.add("Статус: НЕУДАЧА");
            findingsList.add("Номер слишком короткий или слишком длинный");
            return findingsList;
        }
        // проверка на недопустимы символы
        if (filters.USES_ONLY_ALLOWED_LETTERS.negate().test(canonicalView)) {
            findingsList.add("Статус: НЕУДАЧА");
            findingsList.add("Номер содержит недопустимые символы");
            return findingsList;
        }
        // проверка на присутствие нкода региона
        if (filters.HAS_REGION_CODE.negate().test(canonicalView)) {
            findingsList.add("Статус: НЕУДАЧА");
            findingsList.add("Номер не содержит кода региона\n");
            return findingsList;
        }
        // проверка на соответсвие формата по ГОСТу
        if (filters.VALIDATE_FORMAT.negate().test(canonicalView)) {
            findingsList.add("Статус: НЕУДАЧА");
            findingsList.add("Номер не соответствует формату номеров гражданских авто в РФ");
            return findingsList;
        }
        // за одно ищем код региона в базе
        if (filters.VALIDATE_REGION_CODE.negate().test(canonicalView)) {
            findingsList.add("Статус: ЧАСТИЧНЫЙ УСПЕХ");
            findingsList.add("Номер соответствует всем правилам");
            findingsList.add("Номер не находится в базе кодов регионов");
            findingsList.add(filters.GET_PLATE_TYPE.apply(canonicalView).toString());
        }
        return findingsList;
    }

    class Filters {

        // проверяет на паттерн из 4А
        private final Predicate<String> is4ATypeCode = plate -> {
            String alpha = String.join("", getContext().getAllowed_alphabet());
            return plate.matches("[" + alpha + "]{2}[0-9]{6}]");
        };

        // парсит код региона
        private final UnaryOperator<String> getRegionCode = plate -> {
            String regionCodeParsed;
            if (is4ATypeCode.test(plate)) {
                regionCodeParsed = plate.substring(3,5);
            } else {
                String suffix = plate.substring(plate.length() - 3);
                if (Character.isDigit(suffix.charAt(0))) {
                    regionCodeParsed = suffix;
                } else {
                    regionCodeParsed = suffix.substring(1);
                }
            }
            Objects.requireNonNull(regionCodeParsed);
            return regionCodeParsed;
        };

        // проверяет, есть ли код региона, если знак типа 4А tldr транспортный знак внедорожных тс
        private final Predicate<String> hasRegionType4A = plate ->
                plate.matches("[A-Z]{2}\\d\\d[0-9]{4}");

        // проверка на наличие кода региона у обычных номеров
        private final Predicate<String> hasRegion_normal = plate ->
                plate.matches(".*\\d\\d\\d?");


        // длина знака должна быть между 7 или 9 символами
        public Predicate<String> LENGTH_FILTER =
                (plate) -> plate.length() >= 7 && plate.length() <= 9;


        // проверка на наличие кода региона в номере
        public Predicate<String> HAS_REGION_CODE = plate ->
                hasRegion_normal.or(hasRegionType4A).test(plate);

        // проверяет на наличие недопустимых символов в номере
        public Predicate<String> USES_ONLY_ALLOWED_LETTERS = plate -> {
               String alphabet = String.join("", getContext()
                       .getAllowed_alphabet());
               String numbers = "0123456789";
               String regx = "[^" + alphabet + numbers + "]";
               String plateSymbolsFiltered = plate.replaceAll(regx, "");
               return plate.length() == plateSymbolsFiltered.length();
        };

        // валидирует код региона
        public Predicate<String> VALIDATE_REGION_CODE = plate -> {
            String regionCode = getRegionCode.apply(plate);
            return context.getRegions()
                    .stream()
                    .flatMap(region -> region.getPlateCodes().stream())
                    .anyMatch(regionCode::equals);
        };

        // ищет регион по знаку
        public Function<String, Region> GET_REGION = plate -> getContext()
                .getRegions()
                .stream()
                .filter(region -> region.getPlateCodes().contains(getRegionCode.apply(plate)))
                .findFirst()
                .orElse(new Region("Неизвестный регион", new HashSet<>()));

        // ищет тип знака
        public Function<String, PlateType> GET_PLATE_TYPE = plate -> getContext().getPlateTypes()
                     .stream()
                     .filter(plateType -> plate.matches(plateType.getRegexp()))
                     .findFirst()
                     .orElse(new PlateType("NN",
                                     "Формат неизвестен",
                                 "Неизвестный тип знака",
                                             "пусто"));

        //  прогоняем номер по регуляркам из конфига
        // если не одна не матчится, тот номер или неверный или не гражданский
        public Predicate<String> VALIDATE_FORMAT = plate -> getContext().getPlateTypes()
                .stream()
                .map(PlateType::getRegexp)
                .anyMatch(plate::matches);
    }
}









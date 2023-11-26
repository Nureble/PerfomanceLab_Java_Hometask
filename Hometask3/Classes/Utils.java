package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;


// утилитный класс для выноса неприглядностей подальше от бизнес логики
public class Utils {

    private Utils() {
    }

    // регистрируем наш кастомный маппер и регистрируем его
    public static <T> ObjectMapper getCustomObjectMapper(Class<T> clazz, StdDeserializer<T> customSerializer) {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(clazz, customSerializer);
        mapper.registerModule(module);
        return mapper;
    }

    // по дефолту грузится конфиг json, который лежит в ресурсах нашего толстого jar
    public static String loadJSONAsString(String jsonConfigFileName) {
        try (InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream(jsonConfigFileName);
             BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(in)));
             Stream<String> stream = br.lines()) {
            return stream.collect(Collectors.joining());
        } catch (IOException e) {
            throw new RuntimeException("Файл конфига не был загружен");
        }
    }

    // можно передать конфиг файл через аргументы командной строки
    public static String loadJSONAsString(Path path) {
        try (Stream<String> stream = Files.lines(path)) {
            return stream.collect(Collectors.joining());
        } catch (IOException e) {
            throw new RuntimeException("Файл конфигурации не мог быть загружен");
        }
    }


    // конфиг или из файла в jar или из аргуементов командной строки
    public static Context loadConfig(String[] args) {
        String json;
        if (args.length == 0) {
            json = Utils.loadJSONAsString("config.json");
        } else {
            Path pathToJSONConfig = Paths.get(args[0]);
            json = Utils.loadJSONAsString(pathToJSONConfig);
        }
        ObjectMapper mapper = Utils.getCustomObjectMapper(Context.class, new ContextDeserializer());

        try {
            return mapper.readValue(json, Context.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Конфиг не парсится. Невалидный json или сломанный десериализатор");
        }
    }

    // вывод справки в stdin
    public static void printHelp() {
        String message = "Справка:\n";
        String tmp1 = "Введите номерной знак автомобиля в любом из форматов\n";
        String example = "Например:\n";
        String tmp2 = "М000ММ55 или М 000 ММ 555\n";
        String tmp3 = "М000ММ55 или М000ММ555\n";
        String tmp4 = "М000ММRUS55 или М000ММRUS555\n";
        String tmp5 = "М 000 ММ RUS 55 или М 000 ММ RUS 555\n";
        String tmp6 = "Где:\n";
        String legend1 = "M - дозволенная буква кириллического или латинского алфавита в произвольном регистре\n";
        String legend2 = "0 - цифра от 0-9\n";
        String legend3 = "5 - цифра кода региона\n";
        String legend4 = "M может быть А,В,Е,К,М,Н,О,Р,С,Т,У,X\n";
        String ex = "exit - выход из программы\n";
        String h = "help - вывод этого сообщения\n";
        String helpString = message +
                tmp1 +
                example +
                tmp2 +
                tmp3 +
                tmp4 +
                tmp5 + tmp6 + legend1 + legend2 + legend3 + legend4 + ex + h;
        System.out.println(helpString);
    }

    // вечный цикл
    public static void startLoop(Context cntx) {
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8.name());
        while (true) {
            String input = scanner.nextLine().trim();
            System.out.println("Вы ввели номер: " + input);
            if (input.isEmpty()) {
                System.out.println();
                continue;
            }
            if (input.equals("help") || input.equals("info")) {
                Utils.printHelp();
            } else if (input.equals("exit") || input.equals("close")) {
                System.out.println("Программа закрывается.....");
                System.out.println("До свидания");
                break;
            } else {
                Inspector inspector = new Inspector(input, cntx);
                List<String> report = inspector.inspect();
                report.forEach(System.out::println);
            }
        }
        scanner.close();
    }
}


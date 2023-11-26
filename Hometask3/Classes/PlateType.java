package org.example;

import java.util.Objects;

public class PlateType {
    private final String name;
    private final String plateFormat;
    private final String description;
    private final String regexp;

    @Override
    public String toString() {
        return "Тип номерного знака\n" +
                "название: " + name + "\n" +
                "формат: " + plateFormat + "\n" +
                "описание: " + description + "\n" +
                "регулярное выражение: " + regexp + "\n";
    }

    public PlateType(String name, String plateFormat, String description, String regexp) {
        this.name = name;
        this.plateFormat = plateFormat;
        this.description = description;
        this.regexp = regexp;
    }

    public String getName() {
        return name;
    }

    public String getPlateFormat() {
        return plateFormat;
    }

    public String getDescription() {
        return description;
    }

    public String getRegexp() {
        return regexp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlateType plateType = (PlateType) o;
        return Objects.equals(name, plateType.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

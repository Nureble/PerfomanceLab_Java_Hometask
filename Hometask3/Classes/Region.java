package org.example;

import java.util.Objects;
import java.util.Set;

public class Region {

    private final String regionName;
    private final Set<String> plateCodes;

    public Region(String regionName, Set<String> plateCodes) {
        this.regionName = regionName;
        this.plateCodes = plateCodes;
    }

    @Override
    public String toString() {
        return "Регион: " + regionName  + "\n" +
                "Коды региона=" + plateCodes + "\n";
    }

    public String getRegionName() {
        return regionName;
    }

    public Set<String> getPlateCodes() {
        return plateCodes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Region region = (Region) o;
        return Objects.equals(regionName, region.regionName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(regionName);
    }
}

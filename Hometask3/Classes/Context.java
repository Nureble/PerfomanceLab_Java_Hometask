package org.example;
import java.util.Set;

public class Context {
    private final String name;
    private final String standard;
    private final String schema_URL;
    private final Set<Region> regions;
    private final Set<PlateType> plateTypes;
    private final Set<String> allowed_alphabet;

    private Context(String name, String standard, String schema_URL, Set<Region> regions, Set<PlateType> plateTypes, Set<String> allowed_alphabet) {
        this.name = name;
        this.standard = standard;
        this.schema_URL = schema_URL;
        this.regions = regions;
        this.plateTypes = plateTypes;
        this.allowed_alphabet = allowed_alphabet;
    }


    public String getName() {
        return name;
    }

    public String getStandard() {
        return standard;
    }

    public String getSchema_URL() {
        return schema_URL;
    }

    public Set<Region> getRegions() {
        return regions;
    }

    public Set<PlateType> getPlateTypes() {
        return plateTypes;
    }

    public Set<String> getAllowed_alphabet() {
        return allowed_alphabet;
    }

    static class Builder {
        Builder(){}

        private String name;
        private String standard;
        private String schema_URL;
        private Set<Region> regions;
        private Set<PlateType> plateTypes;
        private Set<String> allowed_alphabet;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setStandard(String standard) {
            this.standard = standard;
            return this;
        }

        public Builder setSchema_URL(String schema_URL) {
            this.schema_URL = schema_URL;
            return this;
        }

        public Builder setRegions(Set<Region> regions) {
            this.regions = regions;
            return this;
        }

        public Builder setPlateTypes(Set<PlateType> plateTypes) {
            this.plateTypes = plateTypes;
            return this;
        }

        public Builder setAllowed_alphabet(Set<String> allowed_alphabet) {
            this.allowed_alphabet = allowed_alphabet;
            return this;
        }

        public Context build() {
            return new Context(this.name, this.standard, this.schema_URL, this.regions, this.plateTypes, this.allowed_alphabet);
        }
    }
}

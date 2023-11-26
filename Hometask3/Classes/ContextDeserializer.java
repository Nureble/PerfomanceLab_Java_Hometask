package org.example;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.*;

public class ContextDeserializer extends StdDeserializer<Context> {

    public ContextDeserializer() {
        this(null);
    }

    public ContextDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Context deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode node = p.getCodec().readTree(p);
        Iterator<JsonNode> regions = node.get("regions").elements();

        Set<Region> regionSet = new HashSet<>();

        while (regions.hasNext()) {
            JsonNode region = regions.next();

            String regionName = region.get("region_name").asText();
            Set<String> plateCodesSet = new HashSet<>();

            Iterator<JsonNode> plateCodesNode = region.get("plate_codes").elements();
            while (plateCodesNode.hasNext()) {
                String plate = plateCodesNode.next().asText();
                plateCodesSet.add(plate);
            }
            regionSet.add(new Region(regionName, plateCodesSet));
        }


        Set<PlateType> plateTypesSet = new HashSet<>();

        Iterator<JsonNode> plateTypes = node.get("plate_types").elements();
        while (plateTypes.hasNext()) {

            JsonNode plateTypeObj = plateTypes.next();

            String name = plateTypeObj.get("name").asText();
            String plateFormat = plateTypeObj.get("plate_format").asText();
            String description = plateTypeObj.get("description").asText();
            String regexp = plateTypeObj.get("regexp").asText();

            plateTypesSet.add(new PlateType(name, plateFormat, description, regexp));

        }

        Set<String> alphabetSet = new HashSet<>();

        Iterator<JsonNode> iterOverAlphabet = node.get("allowed_alphabet").elements();
        while (iterOverAlphabet.hasNext()) {
            String letter = iterOverAlphabet.next().asText();
            alphabetSet.add(letter);
        }


        String contextName = node.get("name").asText();
        String standard = node.get("standard").asText();
        String schemaUrl = node.get("schema_URL").asText();

        return new Context.Builder().setName(contextName)
                                    .setSchema_URL(schemaUrl)
                                    .setStandard(standard)
                                    .setAllowed_alphabet(alphabetSet)
                                    .setRegions(regionSet)
                                    .setPlateTypes(plateTypesSet)
                                    .build();

    }
}

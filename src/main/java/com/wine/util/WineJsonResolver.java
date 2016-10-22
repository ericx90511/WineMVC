package com.wine.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Files;
import com.wine.domain.Wine;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by erickang on 10/20/16.
 */
public class WineJsonResolver {

    private static final String JSON_FILE = "src/main/resources/file/wines.json";

    private WineJsonResolver() {}

    public static void readJsonFile() throws IOException {
        File jsonFile = new File(JSON_FILE);
        InputStream inputStream = Files.asByteSource(jsonFile).openStream();
        ObjectMapper mapper = new ObjectMapper();
        List<Wine> wines = mapper.readValue(inputStream, mapper.getTypeFactory().
                constructCollectionType(List.class, Wine.class));
    }
}

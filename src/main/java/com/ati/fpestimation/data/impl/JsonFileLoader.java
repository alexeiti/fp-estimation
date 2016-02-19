package com.ati.fpestimation.data.impl;

import com.ati.fpestimation.domain.AppStackType;
import com.ati.fpestimation.domain.AppType;
import com.ati.fpestimation.domain.EstimationFactor;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonFileLoader {

    private static final String CONFIG_FOLDER = "json_config/";

    protected static List<AppStackType> loadAllStackTypes() throws IOException {
        final String fileName = "stackTypes.cfg";
        ObjectMapper mapper = new ObjectMapper();

        List<AppStackType> sampleObject = mapper.readValue(getResourceFile(fileName), new TypeReference<List<AppStackType>>() {
        });

        System.out.println(sampleObject);
        return sampleObject;
    }


    protected static List<AppType> loadAllAppTypes() throws IOException {
        final String fileName = "appTypes.cfg";
        ObjectMapper mapper = new ObjectMapper();

        List<AppType> sampleObject = mapper.readValue(getResourceFile(fileName), new TypeReference<List<AppType>>() {
        });

        System.out.println(sampleObject);
        return sampleObject;
    }


    private static File getResourceFile(String fileName) {
        //Get file from resources folder
        ClassLoader classLoader = new JsonFileLoader().getClass().getClassLoader();
        return new File(classLoader.getResource(CONFIG_FOLDER + fileName).getFile());
    }

    public static List<EstimationFactor> loadAllEstimationFactors() throws IOException {
        final String fileName = "estimationFactors.cfg";
        ObjectMapper mapper = new ObjectMapper();

        List<EstimationFactor> sampleObject = mapper.readValue(getResourceFile(fileName), new TypeReference<List<EstimationFactor>>() {
        });

        System.out.println(sampleObject);
        return sampleObject;
    }
}

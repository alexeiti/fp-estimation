package com.ati.fpestimation;

import com.ati.fpestimation.domain.kpi.AppStackType;
import com.ati.fpestimation.domain.kpi.AppType;
import com.ati.fpestimation.domain.kpi.EstimationFactor;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonTest {

    public static final String FILE_NAME = "user.json";

    public static void main(String[] args) throws IOException {
        System.out.println("Test");
        // write();
        //  read();

        writeAppList();
        // writeEstimationList();
        // readList();

    }

    private static void write() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        AppStackType sampleObject = new AppStackType(1, "TFrame", "TF");
        AppType appType = new AppType("Tibco", sampleObject.getId());

        //Convert object to JSON string and save into file directly
        mapper.writeValue(new File(FILE_NAME), appType);

        //Convert object to JSON string
        String jsonInString = mapper.writeValueAsString(appType);
        System.out.println(jsonInString);

        //Convert object to JSON string and pretty print
        jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(appType);
        System.out.println(jsonInString);

    }

    private static void read() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        AppType sampleObject = mapper.readValue(new File(FILE_NAME), AppType.class);
        System.out.println(sampleObject);

    }


    private static void writeList() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        AppStackType stack1 = new AppStackType(1, "TFrame", "TF");
        AppStackType stack2 = new AppStackType(2, "Middleware", "MW");
        List<AppStackType> stackList = new ArrayList<>();
        stackList.add(stack1);
        stackList.add(stack2);

        //Convert object to JSON string and save into file directly
        mapper.writeValue(new File(FILE_NAME), stackList);

        //Convert object to JSON string
        String jsonInString = mapper.writeValueAsString(stackList);
        System.out.println(jsonInString);

        //Convert object to JSON string and pretty print
        jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(stackList);
        System.out.println(jsonInString);

    }


    private static void writeAppList() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<Integer> factorList = new ArrayList<>();
        factorList.add(1);
        factorList.add(2);

        AppType sampleObject1 = new AppType("Tibco", 1);
        sampleObject1.setEstimationFactors(factorList);
        AppType sampleObject2 = new AppType("Tibco1", 2);
        List<AppType> appList = new ArrayList<>();
        sampleObject2.setEstimationFactors(factorList);
        appList.add(sampleObject1);
        appList.add(sampleObject2);

        //Convert object to JSON string and save into file directly
        mapper.writeValue(new File(FILE_NAME), appList);

        //Convert object to JSON string
        String jsonInString = mapper.writeValueAsString(appList);
        System.out.println(jsonInString);

        //Convert object to JSON string and pretty print
        jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(appList);
        System.out.println(jsonInString);

    }

    private static void writeEstimationList() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        EstimationFactor sampleObject1 = new EstimationFactor(1, "Tframe Without gui", 4.4);
        EstimationFactor sampleObject2 = new EstimationFactor(2, "Tframe With gui", 5.3);
        List<EstimationFactor> appList = new ArrayList<>();
        appList.add(sampleObject1);
        appList.add(sampleObject2);

        //Convert object to JSON string and save into file directly
        mapper.writeValue(new File(FILE_NAME), appList);

        //Convert object to JSON string
        String jsonInString = mapper.writeValueAsString(appList);
        System.out.println(jsonInString);

        //Convert object to JSON string and pretty print
        jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(appList);
        System.out.println(jsonInString);

    }

    private static void readList() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        List<AppStackType> sampleObject = mapper.readValue(new File(FILE_NAME), ArrayList.class);
        System.out.println(sampleObject);

    }
}

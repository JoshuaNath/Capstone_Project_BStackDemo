package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class TestDataLoader {

    public static Map<String, Object> loadJson(String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new File(filePath), Map.class);
        } catch (IOException e) {
            throw new RuntimeException("Error loading test data file: " + filePath, e);
        }
    }
}

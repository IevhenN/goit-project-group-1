package exchange.julia.telegram;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public class ConfigLoader {
    private static final String CONFIG_FILE_NAME = "config.json";

    public static Map<String, Object> loadConfig() throws IOException {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(CONFIG_FILE_NAME)) {
            return gson.fromJson(reader, Map.class);
        }
    }
}

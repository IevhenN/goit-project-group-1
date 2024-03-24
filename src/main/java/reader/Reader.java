package reader;

import chat.ChatSettings;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static settings.Constants.CONFIG_FILE_NAME;
import static settings.Constants.SETTINGS_DIRECTORY;

public class Reader {
    public ChatSettings readData(long chatID) {
        File directory = new File(SETTINGS_DIRECTORY);
        if (!directory.exists()) {
            boolean success = directory.mkdirs();
            if (!success) {
                System.err.println("Не вдалося створити директорію " + directory.getPath());
            }
        }
        String fileName = SETTINGS_DIRECTORY  + chatID + ".json";
        File file = new File(fileName);
        if (!file.exists()) {
            return null;
        }
        try (FileReader reader = new FileReader(file)) {
            Gson gson = new Gson();
            return gson.fromJson(reader, ChatSettings.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Long> getAllChatID() {
        File directory = new File(SETTINGS_DIRECTORY);
        if (!directory.exists()) {
            boolean success = directory.mkdirs();
            if (!success) {
                System.err.println("Не вдалося створити директорію " + directory.getPath());
            }
        }
        List<Long> chatIDs = new ArrayList<>();
        for (File file : directory.listFiles()) {
            if (file.isFile()) {
                String fileName = file.getName();
                try {
                    long chatID = Long.parseLong(fileName.substring(0, fileName.lastIndexOf('.')));
                    chatIDs.add(chatID);
                } catch (NumberFormatException ignored) {
                }
            }
        }
        return chatIDs;
    }
    public static Map<String, Object> readInit() {
        File directory = new File(SETTINGS_DIRECTORY);
        if (!directory.exists()) {
            boolean success = directory.mkdirs();
            if (!success) {
                System.err.println("Не вдалося створити директорію " + directory.getPath());
            }
        }
        String filePath = SETTINGS_DIRECTORY + CONFIG_FILE_NAME;
        try (FileReader fileReader = new FileReader(filePath)) {
            JsonObject jsonObject = JsonParser.parseReader(fileReader).getAsJsonObject();
            Gson gson = new Gson();
            return gson.fromJson(jsonObject, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

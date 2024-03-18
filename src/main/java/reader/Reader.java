package reader;

import chat.ChatSettings;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static settings.Constants.SETTINGS_DIRECTORY;

public class Reader {
    public ChatSettings readData(long chatID) {
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
        List<Long> chatIDs = new ArrayList<>();
        File directory = new File(SETTINGS_DIRECTORY);
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        String fileName = file.getName();
                        long chatID;
                        try {
                            chatID = Long.parseLong(fileName.substring(0, fileName.lastIndexOf('.')));
                            chatIDs.add(chatID);
                        } catch (NumberFormatException e) {
                        }
                    }
                }
            }
        }
        return chatIDs;
    }
}

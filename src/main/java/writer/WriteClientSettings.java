package writer;

import chat.ChatSettings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static settings.Constants.SETTINGS_DIRECTORY;

public class WriteClientSettings implements WriterData {
    private static final String FILE_EXTENSION = ".json";

    @Override
    public boolean writeData(ChatSettings chatSettings) {
        File directory = new File(SETTINGS_DIRECTORY);
        if (!directory.exists()) {
            boolean success = directory.mkdirs();
            if (!success) {
                System.err.println("Не вдалося створити директорію " + directory.getPath());
            }
        }
        String fileName = SETTINGS_DIRECTORY + chatSettings.getChatID() + FILE_EXTENSION;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(fileName)) {
            gson.toJson(chatSettings, writer);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}

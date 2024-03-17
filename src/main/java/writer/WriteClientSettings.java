package writer;

import chat.ChatSettings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static settings.Constants.SETTINGS_DIRECTORY;

public class WriteClientSettings implements WriterData {

    private static final String FILE_EXTENSION = ".json";

    @Override
    public boolean writeData(ChatSettings chatSettings) {
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

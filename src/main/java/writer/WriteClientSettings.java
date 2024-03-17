package writer;

import chat.ChatSettings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class WriteClientSettings implements WriterData<Object> {

    private static final String FILE_EXTENSION = ".json";

    @Override
    public boolean writeData(List<ChatSettings> data, long chatId) {
           String fileName = chatId + FILE_EXTENSION;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(fileName)) {
            gson.toJson(data, writer);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}

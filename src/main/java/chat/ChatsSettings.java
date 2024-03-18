package chat;

import reader.Reader;
import writer.WriteClientSettings;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatsSettings {
    private static ChatsSettings instance = null;
    private final Map<Long, ChatSettings> chatsSettings;

    private ChatsSettings() {
        this.chatsSettings = new HashMap<>();
    }

    public static synchronized ChatsSettings getInstance() {
        if (instance == null) {
            instance = new ChatsSettings();
        }
        return instance;
    }


    public void loadSettingsFromFiles() {
        Reader reader = new Reader();
        List<Long> chatIDs = reader.getAllChatID();
        for (long chatID : chatIDs) {
            ChatSettings settings = reader.readData(chatID);
            if (settings != null) {
                chatsSettings.put(chatID, settings);
            }
        }
    }

    public void addSettings(ChatSettings settings) {
        chatsSettings.put(settings.getChatID(), settings);
        WriteClientSettings writer = new WriteClientSettings();
        writer.writeData(settings);
    }

    public ChatSettings getChatSettings(long chatID) {
        return chatsSettings.getOrDefault(chatID, new ChatSettings());
    }

}

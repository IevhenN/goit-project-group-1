import chat.ChatsSettings;
import telegram.TelegramBotService;

public class AppLauncher {
    public static void main(String[] args) {
        ChatsSettings.getInstance().loadSettingsFromFiles();
        TelegramBotService botService = new TelegramBotService();
    }
}

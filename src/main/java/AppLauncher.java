import chat.ChatsSettings;
import scheduled.ScheduledMessageSender;
import telegram.TelegramBotService;

import java.util.concurrent.ScheduledExecutorService;

public class AppLauncher {
    public static void main(String[] args) {
        ChatsSettings.getInstance().loadSettingsFromFiles();
        new TelegramBotService();
        new ScheduledMessageSender().startScheduling();
    }
}

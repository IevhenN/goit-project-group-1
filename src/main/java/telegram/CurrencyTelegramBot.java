package telegram;

import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import telegram.comand.StartCommand;

import java.io.IOException;
import java.util.Map;

public class CurrencyTelegramBot extends TelegramLongPollingCommandBot {
    Map<String, Object> config;

    {
        try {
            config = ConfigLoader.loadConfig();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    Map<String, Object> telegramConfig = (Map<String, Object>) config.get("telegram");
    Map<String, Object> botConfig = (Map<String, Object>) telegramConfig.get("bot");
    String botUsername = (String) botConfig.get("username");
    String botToken = (String) botConfig.get("token");

    public CurrencyTelegramBot(){
        register(new StartCommand());
    }
    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        System.out.println("Non-command here");
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}

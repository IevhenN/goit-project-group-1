package telegram;


import banks.Bank;
import chat.ChatSettings;
import chat.ChatsSettings;
import currency.Currency;
import exchange.julia.telegram.currency.CurrencyService;
import exchange.julia.telegram.ui.PrintCurrencyService;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import settings.Constants;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.*;

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
//    private final InlineKeyboardMarkup settingsMarkup;

    public CurrencyTelegramBot(){
        register(new StartCommand());
    }
    @Override
    public String getBotUsername() {
        return botUsername;
    }
    @Override
    public void processNonCommandUpdate(Update update) {
        if (update.hasCallbackQuery()) {
            String callbackQuery = update.getCallbackQuery().getData();

            if (callbackQuery.equals("get_information")) {
                System.out.println("get_information");
            } else if (callbackQuery.equals("get_setting")) {
                InlineKeyboard.sendSettingsMessage(this, update);
            } else if (callbackQuery.contains("quantitydigits")) {
                InlineKeyboard.sendQuantityDigitsMessage(this,update);
            } else if (callbackQuery.contains("currency")) {
                InlineKeyboard.sendCurrencyMessage(this,update);
            } else if (callbackQuery.contains("bank")) {
                InlineKeyboard.sendBankMessage(this,update);
            } else if (callbackQuery.contains("timealerts")) {
                InlineKeyboard.sendTimeAlertsMessage(this,update);
            } else {
                System.out.println("Non-command here");
            }
        }
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
    public void sendMessage(long chatID, String message) {
        // Заглушка
        System.out.println("Відправлено повідомлення у чат " + chatID + ": " + message);
    }
}

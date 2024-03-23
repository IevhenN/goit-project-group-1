package telegram;

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
    private final InlineKeyboardMarkup settingsMarkup;

    public CurrencyTelegramBot(){

        register(new StartCommand());
        InlineKeyboardButton commasButton = InlineKeyboardButton
                .builder()
                .text("Кількість знаків після коми")
                .callbackData("quantitydigits")
                .build();
        InlineKeyboardButton bankButton = InlineKeyboardButton
                .builder()
                .text("Банк")
                .callbackData("bank")
                .build();
        InlineKeyboardButton currencyButton = InlineKeyboardButton
                .builder()
                .text("Валюта")
                .callbackData("currency")
                .build();
        InlineKeyboardButton timeButton = InlineKeyboardButton
                .builder()
                .text("Час оповіщення")
                .callbackData("timealerts")
                .build();
        List<List<InlineKeyboardButton>> settingsKeyboard = new ArrayList<>();
        settingsKeyboard.add(Collections.singletonList(commasButton));
        settingsKeyboard.add(Collections.singletonList(bankButton));
        settingsKeyboard.add(Collections.singletonList(currencyButton));
        settingsKeyboard.add(Collections.singletonList(timeButton));
        settingsMarkup = InlineKeyboardMarkup.builder().keyboard(settingsKeyboard).build();
    }
    @Override
    public String getBotUsername() {

        return botUsername;
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        if (update.hasCallbackQuery()) {
            long chatId = update.getCallbackQuery().getMessage().getChatId();
            long messageId = update.getCallbackQuery().getMessage().getMessageId();

            String callbackQuery = update.getCallbackQuery().getData();

            if (callbackQuery.equals("get_information")) {
                System.out.println("get_information");
            } else if (callbackQuery.equals("get_setting")) {
                SendMessage message = new SendMessage();
                message.setText("Налаштування");
                message.setReplyMarkup(settingsMarkup);
                message.setChatId(String.valueOf(chatId));
                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
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
}

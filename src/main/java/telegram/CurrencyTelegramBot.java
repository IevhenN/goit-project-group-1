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
    private final InlineKeyboardMarkup commasMarkup;
    private final InlineKeyboardMarkup timeMarkup;


    public CurrencyTelegramBot(){

        register(new StartCommand());
        InlineKeyboardButton commasButton = InlineKeyboardButton
                .builder()
                .text("Кількість знаків після коми")
                .callbackData("commas")
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
                .callbackData("time")
                .build();
        List<List<InlineKeyboardButton>> settingsKeyboard = new ArrayList<>();
        settingsKeyboard.add(Collections.singletonList(commasButton));
        settingsKeyboard.add(Collections.singletonList(bankButton));
        settingsKeyboard.add(Collections.singletonList(currencyButton));
        settingsKeyboard.add(Collections.singletonList(timeButton));
        settingsMarkup = InlineKeyboardMarkup.builder().keyboard(settingsKeyboard).build();

        InlineKeyboardButton twoButton = InlineKeyboardButton
                .builder()
                .text("2")
                .callbackData("two")
                .build();
        InlineKeyboardButton threeButton = InlineKeyboardButton
                .builder()
                .text("3")
                .callbackData("three")
                .build();
        InlineKeyboardButton fourButton = InlineKeyboardButton
                .builder()
                .text("4")
                .callbackData("four")
                .build();
        List<List<InlineKeyboardButton>> commasKeyboard = new ArrayList<>();
        commasKeyboard.add(Collections.singletonList(twoButton));
        commasKeyboard.add(Collections.singletonList(threeButton));
        commasKeyboard.add(Collections.singletonList(fourButton));

        commasMarkup = InlineKeyboardMarkup.builder().keyboard(commasKeyboard).build();

        InlineKeyboardButton nineButton = InlineKeyboardButton
                .builder()
                .text("9")
                .callbackData("nine")
                .build();
        InlineKeyboardButton tenButton = InlineKeyboardButton
                .builder()
                .text("10")
                .callbackData("ten")
                .build();
        InlineKeyboardButton elevenButton = InlineKeyboardButton
                .builder()
                .text("11")
                .callbackData("eleven")
                .build();
        InlineKeyboardButton twelveButton = InlineKeyboardButton
                .builder()
                .text("12")
                .callbackData("twelve")
                .build();
        InlineKeyboardButton thirteenButton = InlineKeyboardButton
                .builder()
                .text("13")
                .callbackData("thirteen")
                .build();
        InlineKeyboardButton fourteenButton = InlineKeyboardButton
                .builder()
                .text("14")
                .callbackData("fourteen")
                .build();
        InlineKeyboardButton fifteenButton = InlineKeyboardButton
                .builder()
                .text("15")
                .callbackData("fifteen")
                .build();
        InlineKeyboardButton sixteenButton = InlineKeyboardButton
                .builder()
                .text("16")
                .callbackData("sixteen")
                .build();
        InlineKeyboardButton seventeenButton = InlineKeyboardButton
                .builder()
                .text("17")
                .callbackData("seventeen")
                .build();
        InlineKeyboardButton eighteenButton = InlineKeyboardButton
                .builder()
                .text("18")
                .callbackData("eighteen")
                .build();
        InlineKeyboardButton nonnotificationsButton = InlineKeyboardButton
                .builder()
                .text("Вимкнути сповіщення")
                .callbackData("non_notifications")
                .build();
        List<List<InlineKeyboardButton>> timeKeyboard = new ArrayList<>();
        timeKeyboard.add(Arrays.asList(nineButton, tenButton, elevenButton, twelveButton, thirteenButton));
        timeKeyboard.add(Arrays.asList(fourteenButton, fifteenButton, sixteenButton, seventeenButton, eighteenButton));
        timeKeyboard.add(Collections.singletonList(nonnotificationsButton));
        timeMarkup = InlineKeyboardMarkup.builder().keyboard(timeKeyboard).build();
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
            } else if (callbackQuery.equals("commas")) {
                SendMessage message = new SendMessage();
                message.setText("Виберіть кількість знаків після коми");
                message.setReplyMarkup(commasMarkup);
                message.setChatId(String.valueOf(chatId));
                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            } else if (callbackQuery.contains("currency")) {
                InlineKeyboard.sendCurrencyMessage(this,update);
            } else if (callbackQuery.contains("bank")) {
                InlineKeyboard.sendBankMessage(this,update);
            } else if (callbackQuery.equals("time")) {
                SendMessage message = new SendMessage();
                message.setText("Виберіть час сповіщення");
                message.setReplyMarkup(timeMarkup);
                message.setChatId(String.valueOf(chatId));
                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
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

package exchange.julia.telegram;

import currency.Currency;
import exchange.julia.telegram.currency.CurrencyService;
import exchange.julia.telegram.ui.PrintCurrencyService;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import exchange.julia.telegram.comand.StartCommand;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

    private CurrencyService currencyService;
    private PrintCurrencyService printCurrencyService;
    private final InlineKeyboardMarkup settingsMarkup;
    private final InlineKeyboardMarkup commasMarkup;
    private final InlineKeyboardMarkup bankMarkup;
    private final InlineKeyboardMarkup currencysMarkup;
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

        InlineKeyboardButton usdButton = InlineKeyboardButton
                .builder()
                .text("USD")
                .callbackData("get_usd")
                .build();
        InlineKeyboardButton eurButton = InlineKeyboardButton
                .builder()
                .text("EUR")
                .callbackData("get_eur")
                .build();
        List<List<InlineKeyboardButton>> currencysKeyboard = new ArrayList<>();
        currencysKeyboard.add(Collections.singletonList(usdButton));
        currencysKeyboard.add(Collections.singletonList(eurButton));
        currencysMarkup = InlineKeyboardMarkup.builder().keyboard(currencysKeyboard).build();

        InlineKeyboardButton nbuButton = InlineKeyboardButton
                .builder()
                .text("НБУ")
                .callbackData("nbu")
                .build();
        InlineKeyboardButton privatbankButton = InlineKeyboardButton
                .builder()
                .text("ПриватБанк")
                .callbackData("privatbank")
                .build();
        InlineKeyboardButton monoButton = InlineKeyboardButton
                .builder()
                .text("Монобанк")
                .callbackData("monobank")
                .build();
        List<List<InlineKeyboardButton>> bankKeyboard = new ArrayList<>();
        bankKeyboard.add(Collections.singletonList(nbuButton));
        bankKeyboard.add(Collections.singletonList(privatbankButton));
        bankKeyboard.add(Collections.singletonList(monoButton));
        bankMarkup = InlineKeyboardMarkup.builder().keyboard(bankKeyboard).build();

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
        // дописати цифри 11-18
        List<List<InlineKeyboardButton>> timeKeyboard = new ArrayList<>();
        timeKeyboard.add(Collections.singletonList(nineButton));
        timeKeyboard.add(Collections.singletonList(tenButton));
        //дописати цифри 11-18
        timeMarkup = InlineKeyboardMarkup.builder().keyboard(timeKeyboard).build();
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
                double usdRate = currencyService.getRate(Currency.USD);
                String textMessage = printCurrencyService.convert(usdRate, Currency.USD);
                SendMessage responseMessage = new SendMessage();
                responseMessage.setText(textMessage);
                long chatId = update.getCallbackQuery().getMessage().getChatId();
                responseMessage.setChatId(String.valueOf(chatId));
                try {
                    execute(responseMessage);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            } else if (callbackQuery.equals("get_setting")) {
                SendMessage message = new SendMessage();
                message.setText("Налаштування");
                message.setReplyMarkup(settingsMarkup);
                long chatId = update.getCallbackQuery().getMessage().getChatId();
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
                long chatId = update.getCallbackQuery().getMessage().getChatId();
                message.setChatId(String.valueOf(chatId));
                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            } else if (callbackQuery.equals("currency")) {
                SendMessage message = new SendMessage();
                message.setText("Виберіть валюту");
                message.setReplyMarkup(currencysMarkup);
                long chatId = update.getCallbackQuery().getMessage().getChatId();
                message.setChatId(String.valueOf(chatId));
                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            } else if (callbackQuery.equals("bank")) {
                SendMessage message = new SendMessage();
                message.setText("Виберіть банк");
                message.setReplyMarkup(bankMarkup);
                long chatId = update.getCallbackQuery().getMessage().getChatId();
                message.setChatId(String.valueOf(chatId));
                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            } else if (callbackQuery.equals("time")) {
                SendMessage message = new SendMessage();
                message.setText("Виберіть час сповіщення");
                message.setReplyMarkup(timeMarkup);
                long chatId = update.getCallbackQuery().getMessage().getChatId();
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

package telegram;

import banks.Bank;
import chat.ChatSettings;
import chat.ChatsSettings;
import currency.Currency;
import currency.CurrencyRate;

import exchange.julia.telegram.currency.CurrencyService;
import exchange.julia.telegram.ui.PrintCurrencyService;

import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import settings.Constants;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class CurrencyTelegramBot extends TelegramLongPollingCommandBot {
    Map<String, Object> config;

    {
        try {
            config = ConfigLoader.loadConfig();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private final Map<String, Object> telegramConfig = (Map<String, Object>) config.get("telegram");
    private final Map<String, Object> botConfig = (Map<String, Object>) telegramConfig.get("bot");
    private final String botUsername = (String) botConfig.get("username");
    private final String botToken = (String) botConfig.get("token");

    public CurrencyTelegramBot(){
        register(new StartCommand());
    }
    @Override
    public String getBotUsername() {
        return botUsername;
    }
    @Override
    public String getBotToken() {
        return botToken;
    }
    @Override
    public void processNonCommandUpdate(Update update) {
        if (update.hasCallbackQuery()) {
            String callbackQuery = update.getCallbackQuery().getData();

            if (callbackQuery.equals("get_information")) {
                InlineKeyboard.sendInformation(this,update);
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

    private static List<CurrencyRate> getCurrencyInfo(ChatSettings chatSettings){
        return chatSettings.getCurrencies().stream()
                .map(i->chatSettings.getBank().getBank().getCurrencyRateAPI(i))
                .collect(Collectors.toList());
    }

    public static String getInfo(long chatID){
        ChatSettings chatSettings = ChatsSettings.getInstance().getChatSettings(chatID);
        int quantityDigits = chatSettings.getQuantityDigits().getItem();
        List<CurrencyRate> currencyRates = getCurrencyInfo(chatSettings);

        String result ="Курс в "+chatSettings.getBank().getName()+":\n";

        for (CurrencyRate currencyRate:currencyRates) {
            result+="  "+currencyRate.getCurrency().name()+"/"+Constants.NACIONAL_CURRENCY.name()+"\n";
            result+="      Покупка "+String.format("%."+quantityDigits+"f",currencyRate.getBuy())+"\n";
            result+="      Продаж  "+String.format("%."+quantityDigits+"f",currencyRate.getSell())+"\n";
        }

        return result;

    }
}

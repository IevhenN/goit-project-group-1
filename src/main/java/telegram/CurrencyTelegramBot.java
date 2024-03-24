package telegram;

import banks.Bank;
import banks.Monobank;
import chat.ChatSettings;
import chat.ChatsSettings;
import currency.Currency;
import currency.CurrencyRate;

import exchange.julia.telegram.currency.CurrencyService;
import exchange.julia.telegram.ui.PrintCurrencyService;

import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import reader.Reader;
import settings.Constants;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class CurrencyTelegramBot extends TelegramLongPollingCommandBot {
    private static CurrencyTelegramBot instance = null;
    Map<String, Object> initFile = Reader.readInit();


    private CurrencyTelegramBot(){
        register(new StartCommand());
    }

    public static synchronized CurrencyTelegramBot getInstance() {
        if (instance == null) {
            instance = new CurrencyTelegramBot();
        }
        return instance;
    }
    @Override
    public String getBotUsername() {

        return (String) initFile.get("bot-name");
    }
    @Override
    public String getBotToken() {
        return (String) initFile.get("bot-token");
    }
    @Override
    public void processNonCommandUpdate(Update update) {
        if (update.hasCallbackQuery()) {
            String callbackQuery = update.getCallbackQuery().getData();

            if (callbackQuery.equals("get_information")) {
                long chatId = update.getCallbackQuery().getMessage().getChatId();
                InlineKeyboard.sendInformation(chatId);
            } else if (callbackQuery.equals("get_setting")) {
                InlineKeyboard.sendSettingsMessage(update);
            } else if (callbackQuery.contains("quantitydigits")) {
                InlineKeyboard.sendQuantityDigitsMessage(update);
            } else if (callbackQuery.contains("currency")) {
                InlineKeyboard.sendCurrencyMessage(update);
            } else if (callbackQuery.contains("bank")) {
                InlineKeyboard.sendBankMessage(update);
            } else if (callbackQuery.contains("timealerts")) {
                InlineKeyboard.sendTimeAlertsMessage(update);
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

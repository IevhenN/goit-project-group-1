package scheduled;

import banks.Bank;
import banks.CurrencyTrading;
import chat.ChatSettings;
import currency.Currency;
import currency.CurrencyRate;

import telegram.CurrencyTelegramBot;


import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class ScheduledMessageSender {

    private final ScheduledExecutorService scheduler;
    private final CurrencyTelegramBot telegramBot;
    private final PrintCurrencyService printCurrencyService;

    public ScheduledMessageSender(ScheduledExecutorService scheduler, CurrencyTelegramBot telegramBot, PrintCurrencyService printCurrencyService) {
        this.scheduler = scheduler;
        this.telegramBot = telegramBot;
        this.printCurrencyService = printCurrencyService;
    }

    public void startScheduling(ChatSettings chatSettings) {
        LocalDateTime notificationTime = calculateNotificationTime(chatSettings);

        scheduler.schedule(() -> {
            CurrencyRate currencyRate = getExchangeRate(chatSettings);
            String message = printCurrencyService.convert(currencyRate, (ChatSettings) chatSettings.getCurrencies());
            telegramBot.sendMessage(chatSettings.getChatID(), message);
        }, Duration.between(LocalDateTime.now(), notificationTime).toMillis(), TimeUnit.MILLISECONDS);
    }

    private LocalDateTime calculateNotificationTime(ChatSettings chatSettings) {
        LocalTime notificationTime = chatSettings.getTimeAlerts().getLocalTime();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime notificationDateTime = LocalDateTime.of(now.toLocalDate(), notificationTime);
        if (now.isAfter(notificationDateTime)) {
            notificationDateTime = notificationDateTime.plusDays(1);
        }
        return notificationDateTime;
    }

    private CurrencyRate getExchangeRate(ChatSettings chatSettings) {


        Bank bank = chatSettings.getBank();
        Set<Currency> currencies = chatSettings.getCurrencies();


        CurrencyTrading bankInstance;
        switch (bank) {
            case PRIVAT:
                bankInstance = new PrivatBank(); // Приклад, якщо PrivatBank - це клас, що реалізує CurrencyTrading
                break;
            case MONO:
                bankInstance = new Monobank(); // Приклад, якщо Monobank - це клас, що реалізує CurrencyTrading
                break;
            case NBU:
                bankInstance = new NBU(); // Приклад, якщо NBU - це клас, що реалізує CurrencyTrading
                break;
            default:
                throw new IllegalArgumentException("Unsupported bank: " + bank);
        }

        CurrencyRate result = null;
        for (Currency currency : currencies) {
            CurrencyRate rate = bankInstance.getCurrencyRateAPI(currency);
            if (rate != null) {
                result = rate;
                break;
            }
        }

        return result;
    }
}


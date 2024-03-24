package scheduled;

import banks.QuantityDigits;
import chat.ChatSettings;
import currency.Currency;
import currency.CurrencyRate;

import java.util.Set;

public class PrintCurrencyService {
    public String convert(CurrencyRate currencyRate, ChatSettings chatSettings) {
        Currency currency = currencyRate.getCurrency();
        double buyRate = currencyRate.getBuy();
        double sellRate = currencyRate.getSell();

        String formattedBuyRate = formatRate(buyRate, chatSettings.getQuantityDigits());
        String formattedSellRate = formatRate(sellRate, chatSettings.getQuantityDigits());
        String bankName = chatSettings.getBank().toString();
        String currencyName = currency.name();

        String formattedMessage = String.format("Курс %s від банку %s: Купівля = %s, Продаж = %s", currencyName, bankName, formattedBuyRate, formattedSellRate);

        return formattedMessage;
    }

    private String formatRate(double rate, QuantityDigits quantityDigits) {
        String format = "%." + quantityDigits.getItem() + "f";
        return String.format(format, rate);
    }
}

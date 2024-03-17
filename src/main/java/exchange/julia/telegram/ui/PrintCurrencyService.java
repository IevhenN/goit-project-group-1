package exchange.julia.telegram.ui;

import currency.Currency;

public class PrintCurrencyService {
    public String convert(double rate, Currency currency) {
        String template = "Курс %s = %.2f";
        return String.format(template, currency.name(), rate);
    }
}

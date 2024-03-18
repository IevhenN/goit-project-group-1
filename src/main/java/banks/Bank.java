package banks;

import currency.Currency;
import currency.CurrencyRate;

import java.util.Map;

public enum Bank {
    PRIVAT,
    MONO,
    NBU;
    CurrencyTrading bank;
    String API_URL;
    Map<Currency,CurrencyRate> rates;
}

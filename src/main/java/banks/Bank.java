package banks;

import currency.Currency;
import currency.CurrencyRate;

import java.util.List;
import java.util.Map;

public abstract class Bank implements CurrencyTrading {
    String API_URL;
    Map<Currency,CurrencyRate> rates;

    public CurrencyRate getCurrencyRat(Currency currency) {
        return null;
    }


}

package banks;

import currency.Currency;
import currency.CurrencyRate;

import java.io.IOException;

public interface CurrencyTrading {
    public CurrencyRate getCurrencyRateAPI(Currency currency) throws IOException;

}

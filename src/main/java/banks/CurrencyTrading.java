package banks;

import currency.Currency;
import currency.CurrencyRate;

import java.util.List;

public interface CurrencyTrading {
    List<CurrencyRate> getCurrencyRateAPI(Currency currency);
}

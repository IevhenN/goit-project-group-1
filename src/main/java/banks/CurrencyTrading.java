package banks;

import currency.Currency;
import currency.CurrencyRate;
import java.util.List;

public interface CurrencyTrading {
   public CurrencyRate getCurrencyRateAPI(Currency currency);

}

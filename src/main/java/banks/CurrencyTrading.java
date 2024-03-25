package banks;

import currency.Currency;
import currency.CurrencyRate;

import java.io.IOException;
import java.net.ProtocolException;
import java.util.List;

public interface CurrencyTrading {
   public CurrencyRate getCurrencyRateAPI(Currency currency) throws IOException;

}

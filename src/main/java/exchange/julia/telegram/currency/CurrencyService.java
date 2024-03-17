package exchange.julia.telegram.currency;

import currency.Currency;

public interface CurrencyService {
    double getRate(Currency currency);
}

package exchange.julia.telegram.currency;

import currency.Currency;
import lombok.Data;

@Data
public class CurrencyItem {
    private currency.Currency ccy;
    private Currency base_ccy;
    private float buy;
    private float sale;
}

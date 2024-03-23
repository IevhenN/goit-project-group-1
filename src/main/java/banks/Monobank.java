package banks;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.jsoup.Jsoup;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import currency.Currency;
import currency.CurrencyRate;

public class Monobank implements CurrencyTrading {

    private static final String MONO_API_URL = "https://api.monobank.ua/bank/currency";

    @Override
    public CurrencyRate getCurrencyRateAPI(Currency currency) {
        String json;
        try {
            json = Jsoup.connect(MONO_API_URL)
                    .ignoreContentType(true)
                    .get()
                    .body()
                    .text();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Can't connect to MONO API");
        }

        Type typeToken = new TypeToken<List<Map<String, String>>>(){}.getType();
        List<Map<String,String>> mapList = new Gson().fromJson(json, typeToken);

        Map<String, String> result = mapList.stream()
                .filter(it -> it.get("currencyCodeA").equals(currency.getCode()))
                .filter(it -> it.get("currencyCodeB").equals(Currency.UAH.getCode()))
                .findFirst()
                .orElse(Collections.emptyMap());

        String currencyCode = result.get("currencyCodeA");
        for(Currency finalCurrency: Currency.values()) {
            if (currencyCode.equals(finalCurrency.getCode())) {
                return new CurrencyRate(
                        finalCurrency
                        , Double.parseDouble(result.get("rateBuy"))
                        , Double.parseDouble(result.get("rateSell"))
                );
            }
        }
        throw new IllegalStateException("Currency not found: " + currencyCode);
    }


    public static void main(String[] args)  throws IOException {
        Monobank monobank = new Monobank();
        CurrencyRate currencyRate = monobank.getCurrencyRateAPI(Currency.EUR);
        System.out.println("Currency: " + currencyRate.getCurrency());
        System.out.println("Code: " + currencyRate.getCurrency().getCode());
        System.out.println("Buy rate: " + currencyRate.getBuy());
        System.out.println("Sell rate: " + currencyRate.getSell());
    }
}

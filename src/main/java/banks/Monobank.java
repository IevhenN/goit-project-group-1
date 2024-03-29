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
import settings.Constants;

public class Monobank implements CurrencyTrading {
    private static Monobank instance = null;
    private final String ApiUrl = Constants.getInit("MONO_API_URL");
    private Monobank() {
    }
    public static synchronized Monobank getInstance() {
        if (instance == null) {
            instance = new Monobank();
        }
        return instance;
    }
    @Override
    public CurrencyRate getCurrencyRateAPI(Currency currency) {
        String json;
        try {
            json = Jsoup.connect(ApiUrl)
                    .ignoreContentType(true)
                    .get()
                    .body()
                    .text();
        } catch (IOException e) {
            System.err.println("Error occurred while connecting to MONO API: " + e.getMessage());
            return new CurrencyRate(currency, 0, 0);
        }

        Type typeToken = new TypeToken<List<Map<String, String>>>() {
        }.getType();
        List<Map<String, String>> mapList = new Gson().fromJson(json, typeToken);

        Map<String, String> result = mapList.stream()
                .filter(it -> it.get("currencyCodeA").equals(currency.getCode()))
                .filter(it -> it.get("currencyCodeB").equals(Currency.UAH.getCode()))
                .findFirst()
                .orElse(Collections.emptyMap());

        String currencyCode = result.get("currencyCodeA");
        for (Currency finalCurrency : Currency.values()) {
            if (currencyCode.equals(finalCurrency.getCode())) {
                return new CurrencyRate(
                        finalCurrency
                        , Double.parseDouble(result.get("rateBuy"))
                        , Double.parseDouble(result.get("rateSell"))
                );
            }
        }
        return new CurrencyRate(currency, 0, 0);
    }
}
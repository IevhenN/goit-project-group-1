package exchange.ievhen;

import banks.CurrencyTrading;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import currency.Currency;
import currency.CurrencyRate;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class BankExample implements CurrencyTrading {
    @Override
    public CurrencyRate getCurrencyRateAPI(Currency currency) {

        String url = "https://api.privatbank.ua/p24api/pubinfo?json&exchange&coursid=5";

        String json;

        try {
            json = Jsoup
                    .connect(url)
                    .ignoreContentType(true)
                    .get()
                    .body()
                    .text();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Type typeToken = TypeToken
                .getParameterized(List.class, HashMap.class)
                .getType();

        List<Map<String, String>> mapList = new Gson().fromJson(json, typeToken);

        Map<String, String> result = mapList.stream()
                .filter(it -> it.get("ccy").equals(currency.name()))
                .filter(it -> it.get("base_ccy").equals(Currency.UAH.name()))
                .findFirst()
                .orElse(Collections.emptyMap());

        return new CurrencyRate(
                Currency.valueOf(result.get("ccy"))
                , Double.parseDouble(result.get("buy"))
                , Double.parseDouble(result.get("sale"))
        );
    }

    public static void main(String[] args) throws IOException {
        BankExample bankExample = new BankExample();
        System.out.println("USD = " + bankExample.getCurrencyRateAPI(Currency.USD));
        System.out.println("EUR = " + bankExample.getCurrencyRateAPI(Currency.EUR));
    }
}

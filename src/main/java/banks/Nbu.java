package banks;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import currency.Currency;
import currency.CurrencyRate;
import settings.Constants;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Nbu implements CurrencyTrading {
    private static Nbu instance = null;
    private final String ApiUrl = Constants.getInit("NBU_API_URL");

    private Nbu() {
    }

    public static synchronized Nbu getInstance() {
        if (instance == null) {
            instance = new Nbu();
        }
        return instance;
    }

    @Override
    public CurrencyRate getCurrencyRateAPI(Currency currency) {
        try {
            URL url = new URL(ApiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            StringBuilder stringApi = new StringBuilder();
            try (Scanner scanner = new Scanner(connection.getInputStream())) {
                while (scanner.hasNextLine()) {
                    stringApi.append(scanner.nextLine());
                }
            }

            String json = stringApi.toString();
            JsonParser parser = new JsonParser();
            JsonArray jsonArray = parser.parse(json).getAsJsonArray();


            CurrencyRate currencyRate = null;
            for (JsonElement element : jsonArray) {
                JsonObject jsonObject = element.getAsJsonObject();
                String code = jsonObject.get("cc").getAsString();
                if (code.equals(currency.name())) {
                    double rate = jsonObject.get("rate").getAsDouble();
                    currencyRate = new CurrencyRate(currency, rate, rate);
                    break;
                }
            }
            return currencyRate;
        } catch (IOException e) {
            System.err.println("Error occurred while connecting to NBU API: " + e.getMessage());
            return new CurrencyRate(currency, 0, 0);
        }
    }
}








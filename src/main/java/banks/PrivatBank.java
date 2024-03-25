package banks;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import currency.Currency;
import currency.CurrencyRate;
import telegram.CurrencyTelegramBot;

public class PrivatBank implements CurrencyTrading {
    private static PrivatBank instance = null;
    CurrencyTelegramBot bot = CurrencyTelegramBot.getInstance();
    Map<String, Object> initFile = bot.getInitFile();
    private PrivatBank() {
    }

    public static PrivatBank getInstance() {
        if (instance == null) {
            instance = new PrivatBank();
        }
        return instance;
    }

    @Override
    public CurrencyRate getCurrencyRateAPI(Currency currency) {
        CurrencyRate currencyRate = new CurrencyRate(currency,0,0);
        try {
            URL url = new URL((String) initFile.get("PRIVAT_API_URL"));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = input.readLine()) != null) {
                response.append(inputLine);
            }
            input.close();

            JSONArray currencyRates = new JSONArray(response.toString());
            for (int i = 0; i < currencyRates.length(); i++) {
                JSONObject currencyObj = currencyRates.getJSONObject(i);
                String currencyCode = currencyObj.getString("ccy");
                double buyRate = currencyObj.getDouble("buy");
                double sellRate = currencyObj.getDouble("sale");
                if (currencyCode.equals(currency.name())) {
                    currencyRate = new CurrencyRate(currency, buyRate, sellRate);
                    break;
                }
            }
            connection.disconnect();
        } catch (Exception e) {
            System.err.println("Error occurred while fetching currency rate: " + e.getMessage());
        }
        return currencyRate;
    }
}

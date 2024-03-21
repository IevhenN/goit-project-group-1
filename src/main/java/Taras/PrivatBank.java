package Taras;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
class PrivatBank implements CurrencyTrading {
    private static final String API_URL = "https://api.privatbank.ua/p24api/pubinfo?json&exchange&coursid=5";

    @Override
    public void getCurrencyRateAPI() {
        try {
            URL url = new URL(API_URL);
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
                JSONObject currency = currencyRates.getJSONObject(i);
                String currencyCode = currency.getString("ccy");
                double rate = currency.getDouble("buy");
                System.out.println(currencyCode + " = " + rate);
            }

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


package banks;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import currency.Currency;
import currency.CurrencyRate;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;





    public class Nbu implements CurrencyTrading {
        private Nbu() {

        }

        private static Nbu instance = null;

        private static final String NBU_API = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json";

         public static synchronized Nbu getInstance(){
             if (instance == null){
                 instance = new Nbu();
             }
             return instance;
         }


        @Override
        public CurrencyRate getCurrencyRateAPI(Currency currency) throws IOException {

                URL url = new URL(NBU_API);
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
                for (JsonElement element : jsonArray){
                    JsonObject jsonObject = element.getAsJsonObject();
                    String code = jsonObject.get("cc").getAsString();
                    if (code.equals(currency.getCode())){
                        double rate = jsonObject.get("rate").getAsDouble();
                        currencyRate = new CurrencyRate(currency, rate, rate);
                        break;
                    }
                }





            return currencyRate;
        }
    }








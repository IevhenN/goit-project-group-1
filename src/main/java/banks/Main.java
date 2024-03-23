package banks;
import currency.Currency;
import currency.CurrencyRate;
public class Main {
    public static void main(String[] args) {
        PrivatBank privatBank = PrivatBank.getInstance();

        // Перевірка курсу долара США
        CurrencyRate usdRate = privatBank.getCurrencyRateAPI(Currency.USD);
        if (usdRate != null) {
            System.out.println("USD: " + usdRate.getBuy());
        } else {
            System.out.println("Курс валюти USD не знайдено.");
        }

        // Перевірка курсу євро
        CurrencyRate eurRate = privatBank.getCurrencyRateAPI(Currency.EUR);
        if (eurRate != null) {
            System.out.println("EUR: " + eurRate.getBuy());
        } else {
            System.out.println("Курс валюти EUR не знайдено.");
        }
    }
}

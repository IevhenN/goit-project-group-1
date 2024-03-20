package chat;

import banks.Bank;
import banks.QuantityDigits;
import currency.Currency;
import lombok.Data;

import java.util.List;

@Data
public class ChatSettings {
    private long chatID;
    private QuantityDigits quantityDigits = QuantityDigits.TWO;
    private List<Currency> currencies;
    private Bank bank = Bank.PRIVAT;
    private  TimeAlerts timeAlerts = TimeAlerts.NINE;

    public ChatSettings(long chatID) {
        this.chatID = chatID;
        this.currencies.add(Currency.USD);
    }
}

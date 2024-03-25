package chat;

import banks.Bank;
import banks.QuantityDigits;
import currency.Currency;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public class ChatSettings {
    private long chatID;
    private QuantityDigits quantityDigits = QuantityDigits.TWO;
    private Set<Currency> currencies = new HashSet<>();
    private Bank bank = Bank.PRIVAT;
    private TimeAlerts timeAlerts = TimeAlerts.NINE;

    public ChatSettings(long chatID) {
        this.chatID = chatID;
        this.currencies.add(Currency.USD);
    }

    public void setQuantityDigits(QuantityDigits quantityDigits) {
        this.quantityDigits = quantityDigits;
        ChatsSettings.getInstance().addSettings(this);
    }

    public void setCurrencies(Currency currency) {
        if (this.currencies.contains(currency)) {
            this.currencies.remove(currency);
        } else {
            this.currencies.add(currency);
        }
        ChatsSettings.getInstance().addSettings(this);
    }

    public void setBank(Bank bank) {
        this.bank = bank;
        ChatsSettings.getInstance().addSettings(this);
    }

    public void setTimeAlerts(TimeAlerts timeAlerts) {
        this.timeAlerts = timeAlerts;
        ChatsSettings.getInstance().addSettings(this);
    }
}
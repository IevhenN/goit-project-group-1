package chat;

import banks.Bank;
import lombok.Data;

import java.util.Currency;
import java.util.List;

@Data
public class ChatSettings {
    private long chatID;
    private int numberOfDigits;
    private List<Currency> currencies;
    private Bank bank;
    private int notificationTime;
}

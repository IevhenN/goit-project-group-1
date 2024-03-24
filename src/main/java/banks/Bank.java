package banks;

import lombok.Getter;

@Getter
public enum Bank {
    PRIVAT (PrivatBank.getInstance(),"Приват-банк"),
    MONO(Monobank.getInstance(),"Монобанк"),
    NBU (null,"НБУ");

    private final CurrencyTrading bank;
    private final String name;

    Bank(CurrencyTrading bank, String name) {
        this.bank = bank;
        this.name = name;
    }

}

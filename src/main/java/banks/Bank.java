package banks;
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

    public CurrencyTrading getBank() {
        return bank;
    }

    public String getName() {
        return name;
    }
}

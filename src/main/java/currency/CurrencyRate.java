package currency;

public class CurrencyRate {
    Currency currency;
    double buy;
    double sell;

    public CurrencyRate(Currency currency, double buy, double sell) {
        this.currency = currency;
        this.buy = buy;
        this.sell = sell;
    }

    public double getBuy() {
        return buy;
    }

    public double getSell() {
        return sell;
    }

    @Override
    public String toString() {
        return "{" +
                "=" + currency +
                ", buy=" + buy +
                ", sell=" + sell +
                '}';
    }
}

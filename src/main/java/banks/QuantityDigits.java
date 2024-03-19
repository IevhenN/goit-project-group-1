package banks;

public enum QuantityDigits {
    TWO ("two",2),
    THREE("three",3),
    FOUR("four",4);

    String name;
    int item;

    QuantityDigits(String name, int item) {
        this.name = name;
        this.item = item;
    }

    public String getName() {
        return name;
    }

    public int getItem() {
        return item;
    }
}

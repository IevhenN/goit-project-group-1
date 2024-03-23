package banks;

import lombok.Getter;

@Getter
public enum QuantityDigits {
    TWO ("two",2),
    THREE("three",3),
    FOUR("four",4);

    private final String name;
    private final int item;

    QuantityDigits(String name, int item) {
        this.name = name;
        this.item = item;
    }

}

package banks;

import lombok.Getter;

@Getter
public enum QuantityDigits {
    TWO("два", 2),
    THREE("три", 3),
    FOUR("чотири", 4);

    private final String name;
    private final int item;

    QuantityDigits(String name, int item) {
        this.name = name;
        this.item = item;
    }

}

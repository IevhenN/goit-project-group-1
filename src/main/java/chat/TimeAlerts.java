package chat;

import lombok.Getter;

import java.time.LocalTime;

@Getter
public enum TimeAlerts {
    NINE("9",9),
    TEN("10",10),
    ELEVEN("11",11),
    TWELVE("12",12),
    THIRTEEN("13",13),
    FOURTEEN("14",14),
    FIFTEEN("15",15),
    SIXTEEN("16",16),
    SEVENTEEN("17",17),
    EIGHTEEN("18",18),
    TWTY("20",20),
    EMPTY("Вимкнути сповіщення",0);

    private final String name;
    private final int item;


    TimeAlerts(String name, int item) {
        this.name = name;
        this.item = item;
    }

}

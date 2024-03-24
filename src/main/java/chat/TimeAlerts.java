package chat;

import lombok.Getter;

import java.time.LocalTime;

@Getter
public enum TimeAlerts {
    NINE("9",9, LocalTime.of(9,0)),
    TEN("10",10, LocalTime.of(10,0)),
    ELEVEN("11",11, LocalTime.of(11,0)),
    TWELVE("12",12, LocalTime.of(12,0)),
    THIRTEEN("13",13, LocalTime.of(13,0)),
    FOURTEEN("14",14, LocalTime.of(14,0)),
    FIFTEEN("15",15, LocalTime.of(15,0)),
    SIXTEEN("16",16, LocalTime.of(16,0)),
    SEVENTEEN("17",17, LocalTime.of(17,0)),
    EIGHTEEN("18",18, LocalTime.of(18,0)),
    EMPTY("Вимкнути сповіщення",0,null);

    private final String name;
    private final int item;
    private LocalTime localTime;

    TimeAlerts(String name, int item, LocalTime localTime) {
        this.name = name;
        this.item = item;
        this.localTime = localTime;
    }

}

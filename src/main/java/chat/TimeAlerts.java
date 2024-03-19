package chat;

public enum TimeAlerts {
    NINE("nine",9),
    TEN("ten",10),
    ELEVEN("eleven",11),
    TWELVE("twelve",12),
    THIRTEEN("thirteen",13),
    FOURTEEN("fourteen",14),
    FIFTEEN("fifteen",15),
    SIXTEEN("sixteen",16),
    SEVENTEEN("seventeen",17),
    EIGHTEEN("eighteen",18);

    String name;
    int item;

    TimeAlerts(String name, int item) {
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

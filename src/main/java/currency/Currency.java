package currency;

public enum Currency {
    USD ("840"),
    EUR ("978"),
    UAH ("980");

    private String code;

    Currency(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
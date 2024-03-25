package settings;

import currency.Currency;
import reader.Reader;

import java.util.Map;

public class Constants {

    public static String CHECKBOX = "✅";
    public static final String SETTINGS_DIRECTORY = "Data/";
    public static final String CONFIG_FILE_NAME = "init.json";
    public static final Currency NACIONAL_CURRENCY = Currency.UAH;
    private static final Map<String, String> init = Reader.readInit();

    public static String getInit(String name) {
        if (init != null) return init.get(name);
        return "";
    }


}

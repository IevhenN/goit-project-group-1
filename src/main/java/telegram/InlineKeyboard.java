package telegram;

import currency.Currency;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class InlineKeyboard {
    private InlineKeyboard() {

    }
//    далее только статичные методы
    public static InlineKeyboardMarkup getCurrencyMessage(long chatID){
        Stream<Currency> currencyStreamStream = Stream.of(Currency.values());

        currencyStreamStream
                .filter((i)->i!=Currency.UAH)
                .map((i)->
                        InlineKeyboardButton
                                .builder()
                                .text(i.name())
                                .callbackData("get_")
                                .build())
        InlineKeyboardButton usdButton = InlineKeyboardButton
                .builder()
                .text("USD")
                .callbackData("get_usd")
                .build();
        InlineKeyboardButton eurButton = InlineKeyboardButton
                .builder()
                .text("EUR")
                .callbackData("get_eur")
                .build();
        List<List<InlineKeyboardButton>> currencysKeyboard = new ArrayList<>();
        currencysKeyboard.add(Collections.singletonList(usdButton));
        currencysKeyboard.add(Collections.singletonList(eurButton));
        return InlineKeyboardMarkup.builder().keyboard(currencysKeyboard).build();
    }

}

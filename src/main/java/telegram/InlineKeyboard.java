package telegram;

import chat.ChatSettings;
import chat.ChatsSettings;
import currency.Currency;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import settings.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InlineKeyboard {
    private InlineKeyboard() {

    }

    //    далее только статичные методы
    public static InlineKeyboardMarkup getCurrencyMessage(long chatID) {
        ChatSettings chatSettings = ChatsSettings.getInstance().getChatSettings(chatID);

        Stream<Currency> currencyStreamStream = Stream.of(Currency.values());

        List<List<InlineKeyboardButton>> currencysKeyboard = currencyStreamStream
                .filter((i) -> i != Constants.NACIONAL_CURRENCY)
                .map((Currency i) -> {
                    String checkbox = "";
                    if (chatSettings.getCurrencies().contains(i)){
                        checkbox=Constants.CHECKBOX+" ";
                    }
                    InlineKeyboardButton button = InlineKeyboardButton
                            .builder()
                            .text(checkbox+i.name())
                            .callbackData("currency" + i.name())
                            .build();

                    return Collections.singletonList(button);
                })
                .collect(Collectors.toList());

//        InlineKeyboardButton usdButton = InlineKeyboardButton
//                .builder()
//                .text("USD")
//                .callbackData("get_usd")
//                .build();
//        InlineKeyboardButton eurButton = InlineKeyboardButton
//                .builder()
//                .text("EUR")
//                .callbackData("get_eur")
//                .build();
//
//
//        List<List<InlineKeyboardButton>> currencysKeyboard = new ArrayList<>();
//        currencysKeyboard.add(Collections.singletonList(usdButton));
//        currencysKeyboard.add(Collections.singletonList(eurButton));

        return InlineKeyboardMarkup.builder().keyboard(currencysKeyboard).build();
    }

}

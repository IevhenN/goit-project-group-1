package telegram;

import chat.ChatSettings;
import chat.ChatsSettings;
import currency.Currency;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import settings.Constants;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InlineKeyboard {
    private InlineKeyboard() {

    }

    //    далее только статичные методы
    public static InlineKeyboardMarkup getCurrencyKeyboard(long chatID) {
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

    public static void sendCurrencyMassage(CurrencyTelegramBot tBot, Update update){
        long chatId = update.getCallbackQuery().getMessage().getChatId();
        long messageId = update.getCallbackQuery().getMessage().getMessageId();
        String callbackQuery = update.getCallbackQuery().getData();

        if (callbackQuery.equals("currency")){
            // new menu
            SendMessage message = new SendMessage();
            message.setText("Виберіть валюту");
            message.setReplyMarkup(InlineKeyboard.getCurrencyKeyboard(chatId));
            message.setChatId(String.valueOf(chatId));
            try {
                tBot.execute(message);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        } else {
            // The user clicked the button
            String nameEnum = callbackQuery.replace("currency","");
            Currency currency = Currency.valueOf(nameEnum);
            ChatSettings chatSettings = ChatsSettings.getInstance().getChatSettings(chatId);
            chatSettings.setCurrencies(currency);

            EditMessageReplyMarkup editMarkup = new EditMessageReplyMarkup();
                editMarkup.setChatId(chatId);
                editMarkup.setMessageId(Math.toIntExact(messageId));
                editMarkup.setReplyMarkup(InlineKeyboard.getCurrencyKeyboard(chatId));
            try {
                tBot.execute(editMarkup);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

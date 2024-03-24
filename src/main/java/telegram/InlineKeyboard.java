package telegram;

import banks.Bank;
import banks.QuantityDigits;
import chat.ChatSettings;
import chat.ChatsSettings;
import chat.TimeAlerts;
import currency.Currency;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import settings.Constants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InlineKeyboard {
    private InlineKeyboard() {}
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

        return InlineKeyboardMarkup.builder().keyboard(currencysKeyboard).build();
    }
    public static void sendCurrencyMessage(CurrencyTelegramBot tBot, Update update){
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
    public static InlineKeyboardMarkup getBankKeyboard(long chatID) {
        ChatSettings chatSettings = ChatsSettings.getInstance().getChatSettings(chatID);

        Stream<Bank>bankStream = Stream.of(Bank.values());

        List<List<InlineKeyboardButton>> bankKeyboard = bankStream
                    .map((Bank i) -> {
                    String checkbox = "";
                    if (chatSettings.getBank()==i){
                        checkbox=Constants.CHECKBOX+" ";
                    }
                    InlineKeyboardButton button = InlineKeyboardButton
                            .builder()
                            .text(checkbox+i.getName())
                            .callbackData("bank" + i.name())
                            .build();

                    return Collections.singletonList(button);
                })
                .collect(Collectors.toList());

        return InlineKeyboardMarkup.builder().keyboard(bankKeyboard).build();
    }
    public static void sendBankMessage(CurrencyTelegramBot tBot, Update update){
        long chatId = update.getCallbackQuery().getMessage().getChatId();
        long messageId = update.getCallbackQuery().getMessage().getMessageId();
        String callbackQuery = update.getCallbackQuery().getData();

        if (callbackQuery.equals("bank")){
            // new menu
            SendMessage message = new SendMessage();
            message.setText("Виберіть банк");
            message.setReplyMarkup(InlineKeyboard.getBankKeyboard(chatId));
            message.setChatId(String.valueOf(chatId));
            try {
                tBot.execute(message);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        } else {
            // The user clicked the button
            String nameEnum = callbackQuery.replace("bank","");
            Bank bank = Bank.valueOf(nameEnum);
            ChatSettings chatSettings = ChatsSettings.getInstance().getChatSettings(chatId);
            chatSettings.setBank(bank);

            EditMessageReplyMarkup editMarkup = new EditMessageReplyMarkup();
            editMarkup.setChatId(chatId);
            editMarkup.setMessageId(Math.toIntExact(messageId));
            editMarkup.setReplyMarkup(InlineKeyboard.getBankKeyboard(chatId));
            try {
                tBot.execute(editMarkup);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static InlineKeyboardMarkup getQuantityDigitsKeyboard(long chatID) {
        ChatSettings chatSettings = ChatsSettings.getInstance().getChatSettings(chatID);

        Stream<QuantityDigits>quantityDigitsStream = Stream.of(QuantityDigits.values());

        List<List<InlineKeyboardButton>> quantityDigitsKeyboard = quantityDigitsStream
                .map((QuantityDigits i) -> {
                    String checkbox = "";
                    if (chatSettings.getQuantityDigits()==i){
                        checkbox=Constants.CHECKBOX+" ";
                    }
                    InlineKeyboardButton button = InlineKeyboardButton
                            .builder()
                            .text(checkbox+i.getName())
                            .callbackData("quantitydigits" + i.name())
                            .build();

                    return Collections.singletonList(button);
                })
                .collect(Collectors.toList());

        return InlineKeyboardMarkup.builder().keyboard(quantityDigitsKeyboard).build();
    }
    public static void sendQuantityDigitsMessage(CurrencyTelegramBot tBot, Update update){
        long chatId = update.getCallbackQuery().getMessage().getChatId();
        long messageId = update.getCallbackQuery().getMessage().getMessageId();
        String callbackQuery = update.getCallbackQuery().getData();

        if (callbackQuery.equals("quantitydigits")){
            // new menu
            SendMessage message = new SendMessage();
            message.setText("Виберіть кількість знаків після коми");
            message.setReplyMarkup(InlineKeyboard.getQuantityDigitsKeyboard(chatId));
            message.setChatId(String.valueOf(chatId));
            try {
                tBot.execute(message);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        } else {
            // The user clicked the button
            String nameEnum = callbackQuery.replace("quantitydigits","");
            QuantityDigits quantityDigits = QuantityDigits.valueOf(nameEnum);
            ChatSettings chatSettings = ChatsSettings.getInstance().getChatSettings(chatId);
            chatSettings.setQuantityDigits(quantityDigits);

            EditMessageReplyMarkup editMarkup = new EditMessageReplyMarkup();
            editMarkup.setChatId(chatId);
            editMarkup.setMessageId(Math.toIntExact(messageId));
            editMarkup.setReplyMarkup(InlineKeyboard.getQuantityDigitsKeyboard(chatId));
            try {
                tBot.execute(editMarkup);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static InlineKeyboardMarkup getTimeAlertsKeyboard(long chatID) {
        ChatSettings chatSettings = ChatsSettings.getInstance().getChatSettings(chatID);

        Stream<TimeAlerts>timeAlertsStream = Stream.of(TimeAlerts.values());

        List<InlineKeyboardButton> timeAlertsKeyboard = timeAlertsStream
                .map((TimeAlerts i) -> {
                    String checkbox = "";
                    if (chatSettings.getTimeAlerts()==i){
                        checkbox=Constants.CHECKBOX+" ";
                    }
                    return InlineKeyboardButton
                            .builder()
                            .text(checkbox+i.getName())
                            .callbackData("timealerts" + i.name())
                            .build();
                })
                .collect(Collectors.toList());


        return InlineKeyboardMarkup.builder().keyboard(getMultiList(timeAlertsKeyboard)).build();
    }

    private static List<List<InlineKeyboardButton>> getMultiList(List<InlineKeyboardButton> timeAlertsKeyboard) {
        List<List<InlineKeyboardButton>> listOfLists = new ArrayList<>();
        List<InlineKeyboardButton> tempList = new ArrayList<>();
        int count = 0;
        for (InlineKeyboardButton element : timeAlertsKeyboard) {
            tempList.add(element);
            count++;
            if (count % 3 == 0) {
                listOfLists.add(new ArrayList<>(tempList));
                tempList.clear();
            }
        }
        if (!tempList.isEmpty()) {
            listOfLists.add(new ArrayList<>(tempList));
        }

        return listOfLists;
    }

    public static void sendTimeAlertsMessage(CurrencyTelegramBot tBot, Update update){
        long chatId = update.getCallbackQuery().getMessage().getChatId();
        long messageId = update.getCallbackQuery().getMessage().getMessageId();
        String callbackQuery = update.getCallbackQuery().getData();

        if (callbackQuery.equals("timealerts")){
            // new menu
            SendMessage message = new SendMessage();
            message.setText("Виберіть час сповіщення");
            message.setReplyMarkup(InlineKeyboard.getTimeAlertsKeyboard(chatId));
            message.setChatId(String.valueOf(chatId));
            try {
                tBot.execute(message);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        } else {
            // The user clicked the button
            String nameEnum = callbackQuery.replace("timealerts","");
            TimeAlerts timeAlerts = TimeAlerts.valueOf(nameEnum);
            ChatSettings chatSettings = ChatsSettings.getInstance().getChatSettings(chatId);
            chatSettings.setTimeAlerts(timeAlerts);

            EditMessageReplyMarkup editMarkup = new EditMessageReplyMarkup();
            editMarkup.setChatId(chatId);
            editMarkup.setMessageId(Math.toIntExact(messageId));
            editMarkup.setReplyMarkup(InlineKeyboard.getTimeAlertsKeyboard(chatId));
            try {
                tBot.execute(editMarkup);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static InlineKeyboardMarkup getSettingsKeyboard() {
        InlineKeyboardButton commasButton = InlineKeyboardButton
                .builder()
                .text("Кількість знаків після коми")
                .callbackData("quantitydigits")
                .build();
        InlineKeyboardButton bankButton = InlineKeyboardButton
                .builder()
                .text("Банк")
                .callbackData("bank")
                .build();
        InlineKeyboardButton currencyButton = InlineKeyboardButton
                .builder()
                .text("Валюта")
                .callbackData("currency")
                .build();
        InlineKeyboardButton timeButton = InlineKeyboardButton
                .builder()
                .text("Час оповіщення")
                .callbackData("timealerts")
                .build();
        List<List<InlineKeyboardButton>> settingsKeyboard = new ArrayList<>();
        settingsKeyboard.add(Collections.singletonList(commasButton));
        settingsKeyboard.add(Collections.singletonList(bankButton));
        settingsKeyboard.add(Collections.singletonList(currencyButton));
        settingsKeyboard.add(Collections.singletonList(timeButton));
        return InlineKeyboardMarkup.builder().keyboard(settingsKeyboard).build();
    }
    public static void sendSettingsMessage(CurrencyTelegramBot tBot, Update update){
        long chatId = update.getCallbackQuery().getMessage().getChatId();
        SendMessage message = new SendMessage();
        message.setText("Налаштування");
        message.setReplyMarkup(getSettingsKeyboard());
        message.setChatId(String.valueOf(chatId));
        try {
            tBot.execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public static void sendInformation(CurrencyTelegramBot tBot, Update update){
        long chatId = update.getCallbackQuery().getMessage().getChatId();
        SendMessage message = new SendMessage();
        String info = CurrencyTelegramBot.getInfo(chatId);
        message.setText(info);
        message.setChatId(String.valueOf(chatId));
        message.setReplyMarkup(InlineKeyboard.getStartKeyboard());
        try {
            tBot.execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public static InlineKeyboardMarkup getStartKeyboard() {
        InlineKeyboardButton infoButton = InlineKeyboardButton
                .builder()
                .text("Отримати інформацію")
                .callbackData("get_information")
                .build();

        InlineKeyboardButton settingButton = InlineKeyboardButton
                .builder()
                .text("Налаштування")
                .callbackData("get_setting")
                .build();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(Collections.singletonList(infoButton));
        keyboard.add(Collections.singletonList(settingButton));

        return InlineKeyboardMarkup.builder().keyboard(keyboard).build();
    }

}

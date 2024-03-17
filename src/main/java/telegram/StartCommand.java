package telegram;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StartCommand extends BotCommand {
    public StartCommand(){
        super("start", "Start command");
    }
    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String text = "Щиро вітаю! Цей бот допоможе відстежити поточний курс валют";
        SendMessage message = new SendMessage();
        message.setText(text);
        message.setChatId(Long.toString(chat.getId()));
        InlineKeyboardButton infoButton = InlineKeyboardButton
                .builder()
                .text("Отримати інформацію")
                .callbackData("get_information")
                .build();
        InlineKeyboardMarkup keyboardinform = InlineKeyboardMarkup
                .builder()
                .keyboard(Collections.singleton(
                        Collections.singletonList(infoButton)
                ))
                .build();
        InlineKeyboardButton settingButton = InlineKeyboardButton
                .builder()
                .text("Налаштування")
                .callbackData("get_setting")
                .build();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(Collections.singletonList(infoButton));
        keyboard.add(Collections.singletonList(settingButton));

        InlineKeyboardMarkup replyMarkup = InlineKeyboardMarkup.builder().keyboard(keyboard).build();

        message.setReplyMarkup(replyMarkup);
        try{
            absSender.execute(message);
        } catch (TelegramApiException e){
            e.printStackTrace();
        }
    }
}

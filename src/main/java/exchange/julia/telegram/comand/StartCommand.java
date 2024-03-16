package exchange.julia.telegram.comand;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StartCommand extends BotCommand {
    public StartCommand(){
        super("start", "Start command");
    }
    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String text = "Congratulations! This bot will help you track the current exchange rate";
        SendMessage message = new SendMessage();
        message.setText(text);
        message.setChatId(Long.toString(chat.getId()));
        InlineKeyboardButton infoButton = InlineKeyboardButton
                .builder()
                .text("Get information")
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
                .text("Setting")
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

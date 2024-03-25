package telegram;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class StartCommand extends BotCommand {
    public StartCommand() {
        super("start", "Start command");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String text = "Щиро вітаю! Цей бот допоможе відстежити поточний курс валют";
        SendMessage message = new SendMessage();
        message.setText(text);
        message.setChatId(Long.toString(chat.getId()));

        message.setReplyMarkup(InlineKeyboard.getStartKeyboard());
        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}

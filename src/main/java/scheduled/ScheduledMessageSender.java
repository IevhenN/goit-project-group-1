package scheduled;


import chat.ChatSettings;
import telegram.CurrencyTelegramBot;


import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class ScheduledMessageSender {

    private final ScheduledExecutorService scheduler;

    public ScheduledMessageSender(ScheduledExecutorService scheduler) {
        this.scheduler = scheduler;

    }

    public void startScheduling(ChatSettings chatSettings) {
        LocalDateTime notificationTime = calculateNotificationTime(chatSettings);

        scheduler.schedule(() -> {
            CurrencyTelegramBot.getInfo(chatSettings.getChatID());
        }, Duration.between(LocalDateTime.now(), notificationTime).toMillis(), TimeUnit.MILLISECONDS);
    }

    private LocalDateTime calculateNotificationTime(ChatSettings chatSettings) {
        LocalTime notificationTime = chatSettings.getTimeAlerts().getLocalTime();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime notificationDateTime = LocalDateTime.of(now.toLocalDate(), notificationTime);
        if (now.isAfter(notificationDateTime)) {
            notificationDateTime = notificationDateTime.plusDays(1);
        }
        return notificationDateTime;
    }
}


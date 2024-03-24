package scheduled;


import chat.ChatSettings;
import telegram.CurrencyTelegramBot;
import reader.Reader;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class ScheduledMessageSender {


    private final ScheduledExecutorService scheduler;

    public ScheduledMessageSender(ScheduledExecutorService scheduler) {
        this.scheduler = scheduler;

    }
    public void startScheduling() {
        Reader reader = new Reader();
        List<Long> chatIDs = reader.getAllChatID();
        scheduler.scheduleAtFixedRate(() -> {
            LocalDateTime currentDateTime = LocalDateTime.now();
            for (Long chatID : chatIDs) {
                ChatSettings chatSettings = reader.readData(chatID);
                if (chatSettings != null) {
                    LocalTime notificationTime = chatSettings.getTimeAlerts().getLocalTime();
                    if (currentDateTime.toLocalTime().equals(notificationTime)) {
                        CurrencyTelegramBot.getInfo(chatID);
                    }
                }
            }
        }, 0, 1, TimeUnit.HOURS);
    }

//    private LocalDateTime calculateNotificationTime(ChatSettings chatSettings) {
//        LocalTime notificationTime = chatSettings.getTimeAlerts().getLocalTime();
//        LocalDateTime now = LocalDateTime.now();
//        LocalDateTime notificationDateTime = LocalDateTime.of(now.toLocalDate(), notificationTime);
//        if (now.isAfter(notificationDateTime)) {
//            notificationDateTime = notificationDateTime.plusDays(1);
//        }
//        return notificationDateTime;
//    }
}


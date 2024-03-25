package scheduled;


import chat.ChatSettings;
import chat.ChatsSettings;
import chat.TimeAlerts;
import telegram.InlineKeyboard;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledMessageSender {
    private final ScheduledExecutorService scheduler;

    public ScheduledMessageSender() {
        this.scheduler = Executors.newScheduledThreadPool(1);
    }

    private static void processingChatsSettings() {
        System.out.println("Start shad");
        int currentHour = LocalDateTime.now().getHour();
        for (ChatSettings chatSettings : ChatsSettings.getInstance().getChatsSettings().values()) {
            int notificationHour = chatSettings.getTimeAlerts().getItem();
            if (currentHour == notificationHour) {
                InlineKeyboard.sendInformation(chatSettings.getChatID());
            }
        }
    }

    public void startScheduling() {
        List<Integer> hours = Arrays.stream(TimeAlerts.values())
                .map(TimeAlerts::getItem)
                .filter(i -> i != 0)
                .toList();

        for (int hour : hours) {
            LocalTime now = LocalTime.now();
            int currentHour = now.getHour();
            if (currentHour < hour) {
                LocalTime targetTime = LocalTime.of(hour, 0);
                long initialDelay = now.until(targetTime, TimeUnit.MILLISECONDS.toChronoUnit());
                scheduler.scheduleAtFixedRate(ScheduledMessageSender::processingChatsSettings, initialDelay, 24 * 60 * 60 * 1000, TimeUnit.MILLISECONDS);
                break;
            }
        }
    }

}


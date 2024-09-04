package pro.sky.telegrambot.scheduler;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.model.Notification;
import pro.sky.telegrambot.service.NotificationService;

@Component
@RequiredArgsConstructor
@EnableScheduling
public class NotificationScheduler {

    private final NotificationService notificationService;
    private final TelegramBot bot;

    @Scheduled(cron = "0 0/1 * * * *")
    public void run() {
        Notification notification = notificationService.getCurrentNotification();
        if (notification != null) {
            SendMessage sendMessage = new SendMessage(notification.getChatId(), notification.getText());
            bot.execute(sendMessage);
        }
    }
}

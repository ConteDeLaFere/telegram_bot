package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.model.Notification;
import pro.sky.telegrambot.service.NotificationService;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class TelegramBotUpdatesListener implements UpdatesListener {

    @Autowired
    private TelegramBot telegramBot;

    private final NotificationService notificationService;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            log.info("Processing update: {}", update);

            Message message = update.message();
            if (message != null) {

                Long chatId = message.chat().id();
                SendMessage sendMessage;

                if (message.text().equals("/start")) {
                    sendMessage = new SendMessage(chatId, "Welcome to Telegram Bot");
                } else {
                    Notification notification = notificationService.save(chatId, message.text());

                    if (notification != null) {
                        sendMessage = new SendMessage(chatId, "Notification was added");
                    } else {
                        sendMessage = new SendMessage(chatId, "Error! Notification was not added");
                    }
                }

                telegramBot.execute(sendMessage);
            }

        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

}

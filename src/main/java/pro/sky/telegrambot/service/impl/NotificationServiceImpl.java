package pro.sky.telegrambot.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.Notification;
import pro.sky.telegrambot.repository.NotificationRepository;
import pro.sky.telegrambot.service.NotificationService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;

import static pro.sky.telegrambot.util.Patterns.MESSAGE_PATTERN;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    public Notification getCurrentNotification() {
        return notificationRepository.getCurrentNotification();
    }

    @Override
    public Notification save(Long chatId, String message) {
        Matcher matcher = MESSAGE_PATTERN.matcher(message);
        if (matcher.matches()) {
            String dateTime = matcher.group(1);

            LocalDateTime date;
            try {
                date = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
            } catch (DateTimeParseException e) {
                return null;
            }
            String text = matcher.group(3);

            Notification notification = new Notification(chatId, text, date);
            return notificationRepository.save(notification);
        }
        return null;
    }
}

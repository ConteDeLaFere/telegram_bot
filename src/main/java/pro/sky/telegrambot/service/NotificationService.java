package pro.sky.telegrambot.service;

import pro.sky.telegrambot.model.Notification;

public interface NotificationService {

    Notification getCurrentNotification();

    Notification save(Long chatId, String message);
}

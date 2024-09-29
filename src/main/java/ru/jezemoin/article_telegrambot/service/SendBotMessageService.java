package ru.jezemoin.article_telegrambot.service;

public interface SendBotMessageService {
    void sendMessage(String chatId, String message);
}

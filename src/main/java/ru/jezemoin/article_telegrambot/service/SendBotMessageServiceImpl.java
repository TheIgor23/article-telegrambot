package ru.jezemoin.article_telegrambot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.jezemoin.article_telegrambot.bot.JavaTelegramBot;

@Service
public class SendBotMessageServiceImpl implements SendBotMessageService{

    private final JavaTelegramBot javaTelegramBot;

    @Autowired
    public SendBotMessageServiceImpl(JavaTelegramBot javaTelegramBot) {
        this.javaTelegramBot = javaTelegramBot;
    }

    @Override
    public void sendMessage(String chatId, String message) {
        SendMessage sendMessage = SendMessage
                .builder()
                .text(message)
                .chatId(chatId)
                .build();
        sendMessage.enableHtml(true);
        javaTelegramBot.sendAnswerMessage(sendMessage);


    }
}

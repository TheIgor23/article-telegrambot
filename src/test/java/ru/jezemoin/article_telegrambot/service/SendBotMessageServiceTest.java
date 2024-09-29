package ru.jezemoin.article_telegrambot.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.jezemoin.article_telegrambot.bot.JavaTelegramBot;

@DisplayName("Unit-level testing for SendBotMessageService")
public class SendBotMessageServiceTest {
    private SendBotMessageService sendBotMessageService;
    private JavaTelegramBot javaTelegramBot;

    @BeforeEach
    public void init(){
        javaTelegramBot = Mockito.mock(JavaTelegramBot.class);
        sendBotMessageService = new SendBotMessageServiceImpl(javaTelegramBot);
    }

    @Test
    public void shouldProperlySendMessage() throws TelegramApiException {
        String chatId = "test_chat_id";
        String message = "test_message";

        SendMessage sendMessage = SendMessage
                .builder()
                .chatId(chatId)
                .text(message)
                .build();
        sendMessage.enableHtml(true);


        //when
        sendBotMessageService.sendMessage(chatId, message);

        //then
        Mockito.verify(javaTelegramBot).sendAnswerMessage(sendMessage);
    }
}

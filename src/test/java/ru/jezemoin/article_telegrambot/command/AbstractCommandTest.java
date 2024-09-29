package ru.jezemoin.article_telegrambot.command;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.jezemoin.article_telegrambot.bot.JavaTelegramBot;
import ru.jezemoin.article_telegrambot.service.SendBotMessageService;
import ru.jezemoin.article_telegrambot.service.SendBotMessageServiceImpl;

abstract class AbstractCommandTest {
    protected JavaTelegramBot javaTelegramBot = Mockito.mock(JavaTelegramBot.class);
    protected SendBotMessageService sendBotMessageService = new SendBotMessageServiceImpl(javaTelegramBot);

    abstract String getCommandName();

    abstract String getCommandMessage();

    abstract Command getCommand();

    @Test
    public void shouldProperlyExecuteCommand() throws TelegramApiException {
        //given
        Long chatId = 1234567824356L;

        Update update = new Update();
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getChatId()).thenReturn(chatId);
        Mockito.when(message.getText()).thenReturn(getCommandName());
        update.setMessage(message);

        SendMessage sendMessage = SendMessage
                .builder()
                .chatId(chatId.toString())
                .text(getCommandMessage())
                .build();
        sendMessage.enableHtml(true);

        //when
        getCommand().execute(update);

        //then
        Mockito.verify(javaTelegramBot).sendAnswerMessage(sendMessage);
    }
}

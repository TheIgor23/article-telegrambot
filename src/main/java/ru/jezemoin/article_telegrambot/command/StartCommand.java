package ru.jezemoin.article_telegrambot.command;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.jezemoin.article_telegrambot.service.SendBotMessageService;

public class StartCommand implements Command{
    private final SendBotMessageService sendBotMessageService;

    public final static String START_MESSAGE = "Привет. Я могу присылать тебе уведомления о выходе новых статей";

    public StartCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), START_MESSAGE);
    }
}

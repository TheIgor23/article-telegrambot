package ru.jezemoin.article_telegrambot.command;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.jezemoin.article_telegrambot.service.SendBotMessageService;

public class NoCommand implements Command{
    private final SendBotMessageService sendBotMessageService;

    public static final String NO_MESSAGE = "Я поддерживаю команды, начинающиеся со слэша(/).\n"
            + "Чтобы посмотреть список команд введите /help";

    public NoCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), NO_MESSAGE);
    }
}

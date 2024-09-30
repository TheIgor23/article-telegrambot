package ru.jezemoin.article_telegrambot.command;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.jezemoin.article_telegrambot.repository.entity.TelegramUser;
import ru.jezemoin.article_telegrambot.service.SendBotMessageService;
import ru.jezemoin.article_telegrambot.service.TelegramUserService;

public class StartCommand implements Command{
    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;

    public final static String START_MESSAGE = "Привет. Я могу присылать тебе уведомления о выходе новых статей";

    public StartCommand(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();

        telegramUserService.findByChatId(chatId).ifPresentOrElse(
                user-> {
                    user.setActive(true);
                    telegramUserService.save(user);
                },
                () -> {
                    TelegramUser telegramUser = new TelegramUser();
                    telegramUser.setActive(true);
                    telegramUser.setChatId(chatId);
                    telegramUserService.save(telegramUser);
                }
        );

        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), START_MESSAGE);
    }
}

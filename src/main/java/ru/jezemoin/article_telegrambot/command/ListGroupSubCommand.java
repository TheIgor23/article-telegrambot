package ru.jezemoin.article_telegrambot.command;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.jezemoin.article_telegrambot.repository.entity.TelegramUser;
import ru.jezemoin.article_telegrambot.service.SendBotMessageService;
import ru.jezemoin.article_telegrambot.service.TelegramUserService;

import java.util.Optional;
import java.util.stream.Collectors;

public class ListGroupSubCommand implements Command{

    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;

    public ListGroupSubCommand(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public void execute(Update update) {
        //todo add exception handling
        Optional<TelegramUser> telegramUser = telegramUserService.findByChatId(update.getMessage().getChatId().toString());

        if(telegramUser.isEmpty()) {
            return;
        };

        String message = "Я нашел все подписки на группы: \n\n";
        String collectedGroups = telegramUser.get().getGroupSubs().stream()
                .map(it -> "Группа: " + it.getTitle() + " , ID = " + it.getId() + " \n")
                .collect(Collectors.joining());

        sendBotMessageService.sendMessage(telegramUser.get().getChatId(), message + collectedGroups);
    }
}

package ru.jezemoin.article_telegrambot.command;

import jakarta.persistence.EntityNotFoundException;
import org.hibernate.ObjectNotFoundException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.util.CollectionUtils;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.jezemoin.article_telegrambot.repository.entity.GroupSub;
import ru.jezemoin.article_telegrambot.repository.entity.TelegramUser;
import ru.jezemoin.article_telegrambot.service.GroupSubService;
import ru.jezemoin.article_telegrambot.service.SendBotMessageService;
import ru.jezemoin.article_telegrambot.service.TelegramUserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.apache.commons.lang3.StringUtils.isNumeric;
import static ru.jezemoin.article_telegrambot.command.CommandName.DELETE_GROUP_SUB;

public class DeleteGroupSubCommand implements Command{
    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;
    private final GroupSubService groupSubService;

    public DeleteGroupSubCommand(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService, GroupSubService groupSubService) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
        this.groupSubService = groupSubService;
    }

    @Override
    public void execute(Update update) {
        if (update.getMessage().getText().equalsIgnoreCase(DELETE_GROUP_SUB.getCommandName())) {
            sendGroupIdList(update.getMessage().getChatId().toString());
            return;
        }
        String groupId = update.getMessage().getText().split(SPACE)[1];
        String chatId = update.getMessage().getChatId().toString();
        if (isNumeric(groupId)) {
            Optional<GroupSub> optionalGroupSub = groupSubService.findById(Integer.valueOf(groupId));
            if (optionalGroupSub.isPresent()) {
                GroupSub groupSub = optionalGroupSub.get();
                TelegramUser telegramUser = telegramUserService.findByChatId(chatId).orElseThrow(EntityNotFoundException::new);
                groupSub.getUsers().remove(telegramUser);
                groupSubService.save(groupSub);
                sendBotMessageService.sendMessage(chatId, String.format("Удалил подписку на группу: %s", groupSub.getTitle()));
            } else {
                sendBotMessageService.sendMessage(chatId, "Не нашел такой группы =/");
            }
        } else {
            sendBotMessageService.sendMessage(chatId, "неправильный формат ID группы.\n " +
                    "ID должно быть целым положительным числом");
        }
    }

    private void sendGroupIdList(String chatId) {
        String message;
        List<GroupSub> groupSubs = telegramUserService.findByChatId(chatId)
                .orElseThrow(EntityNotFoundException::new)
                .getGroupSubs();

        if (CollectionUtils.isEmpty(groupSubs)) {
            message = "Пока нет подписок на группы. Чтобы добавить подписку напиши /addGroupSub";
        } else {
            String userGroupSubData = groupSubs.stream()
                    .map(group -> String.format("%s - %s \n", group.getTitle(), group.getId()))
                    .collect(Collectors.joining());

            message = String.format("Чтобы удалить подписку на группу - передай команду вместе с ID группы. \n" +
                    "Например: /deleteGroupSub 16 \n\n" +
                    "я подготовил список всех групп, на которые ты подписан) \n\n" +
                    "имя группы - ID группы \n\n" +
                    "%s", userGroupSubData);

        }


        sendBotMessageService.sendMessage(chatId, String.format(message, message));
    }
}

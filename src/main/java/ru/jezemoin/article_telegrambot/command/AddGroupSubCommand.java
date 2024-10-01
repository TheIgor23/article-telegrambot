package ru.jezemoin.article_telegrambot.command;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.jezemoin.article_telegrambot.articleclient.JavaGroupClient;
import ru.jezemoin.article_telegrambot.articleclient.dto.GroupDiscussionInfo;
import ru.jezemoin.article_telegrambot.articleclient.dto.GroupRequestArgs;
import ru.jezemoin.article_telegrambot.repository.entity.GroupSub;
import ru.jezemoin.article_telegrambot.service.GroupSubService;
import ru.jezemoin.article_telegrambot.service.SendBotMessageService;

import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.apache.commons.lang3.StringUtils.isNumeric;
import static ru.jezemoin.article_telegrambot.command.CommandName.ADD_GROUP_SUB;

public class AddGroupSubCommand implements Command{

    private final SendBotMessageService sendBotMessageService;
    private final JavaGroupClient javaRushGroupClient;
    private final GroupSubService groupSubService;

    public AddGroupSubCommand(SendBotMessageService sendBotMessageService,GroupSubService groupSubService, JavaGroupClient javaRushGroupClient
                              ) {
        this.sendBotMessageService = sendBotMessageService;
        this.javaRushGroupClient = javaRushGroupClient;
        this.groupSubService = groupSubService;
    }
    @Override
    public void execute(Update update) {
        if (update.getMessage().getText().equalsIgnoreCase(ADD_GROUP_SUB.getCommandName())) {
            sendGroupIdList(update.getMessage().getChatId().toString());
            return;
        }
        String groupId = update.getMessage().getText().split(SPACE)[1];
        String chatId = update.getMessage().getChatId().toString();
        if (isNumeric(groupId)) {
            GroupDiscussionInfo groupById = javaRushGroupClient.getGroupById(Integer.parseInt(groupId));
            if (isNull(groupById.getId())) {
                sendGroupNotFound(chatId, groupId);
            }
            GroupSub savedGroupSub = groupSubService.save(chatId, groupById);
            sendBotMessageService.sendMessage(chatId, "Подписал на группу " + savedGroupSub.getTitle());
        } else {
            sendGroupNotFound(chatId, groupId);
        }
    }

    private void sendGroupNotFound(String chatId, String groupId) {
        String groupNotFoundMessage = "Нет группы с ID = \"%s\"";
        sendBotMessageService.sendMessage(chatId, String.format(groupNotFoundMessage, groupId));
    }

    private void sendGroupIdList(String chatId) {
        String groupIds = javaRushGroupClient.getGroupList(GroupRequestArgs.builder().build())
                .stream()
                .map(group -> String.format("%s - %s \n", group.getTitle(), group.getId()))
                .collect(Collectors.joining());

        String message = "Чтобы подписаться на группу - передай комадну вместе с ID группы. \n" +
                "Например: /addGroupSub 16. \n\n" +
                "я подготовил список всех групп - выберай какую хочешь :) \n\n" +
                "имя группы - ID группы \n\n" +
                "%s";

        sendBotMessageService.sendMessage(chatId, String.format(message, groupIds));
    }
}

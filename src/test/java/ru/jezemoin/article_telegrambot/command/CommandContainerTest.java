package ru.jezemoin.article_telegrambot.command;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.jezemoin.article_telegrambot.articleclient.JavaGroupClient;
import ru.jezemoin.article_telegrambot.service.GroupSubService;
import ru.jezemoin.article_telegrambot.service.SendBotMessageService;
import ru.jezemoin.article_telegrambot.service.SendBotMessageServiceImpl;
import ru.jezemoin.article_telegrambot.service.TelegramUserService;

import java.util.Arrays;

@DisplayName("Unit-level testing for CommandContainer")
public class CommandContainerTest {
    private CommandContainer commandContainer;

    @BeforeEach
    public void init(){
        SendBotMessageService sendBotMessageService = Mockito.mock(SendBotMessageServiceImpl.class);
        TelegramUserService telegramUserService = Mockito.mock(TelegramUserService.class);
        JavaGroupClient groupClient = Mockito.mock(JavaGroupClient.class);
        GroupSubService groupSubService = Mockito.mock(GroupSubService.class);
        commandContainer = new CommandContainer(sendBotMessageService, telegramUserService, groupClient, groupSubService);
    }

    @Test
    public void shouldGetAllTheExistingCommands() {
        //when-then
        Arrays.stream(CommandName.values())
                .forEach(commandName -> {
                    Command command = commandContainer.retrieveCommand(commandName.getCommandName());
                    Assertions.assertNotEquals(UnknownCommand.class, command.getClass());
                });
    }

    @Test
    public void shouldReturnUnknownCommand() {
        //given
        String unknownCommand = "/sdfg";

        //when
        Command command = commandContainer.retrieveCommand(unknownCommand);

        //then
        Assertions.assertEquals(UnknownCommand.class, command.getClass());
    }
}

package ru.jezemoin.article_telegrambot.command;

import org.junit.jupiter.api.DisplayName;

import static ru.jezemoin.article_telegrambot.command.CommandName.NO;
import static ru.jezemoin.article_telegrambot.command.NoCommand.NO_MESSAGE;

@DisplayName("Unit-level testing for NoCommand")
public class NoCommandTest extends AbstractCommandTest{
    @Override
    String getCommandName() {
        return NO.getCommandName();
    }

    @Override
    String getCommandMessage() {
        return NO_MESSAGE;
    }

    @Override
    Command getCommand() {
        return new NoCommand(sendBotMessageService);
    }
}

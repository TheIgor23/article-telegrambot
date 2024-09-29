package ru.jezemoin.article_telegrambot.command;

public enum CommandName {
    START("/start"),
    STOP("/stop"),
    NO("nocommand"),
    HELP("/help");

    private final String commandName;

    CommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }
}

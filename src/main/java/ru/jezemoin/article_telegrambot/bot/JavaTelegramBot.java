package ru.jezemoin.article_telegrambot.bot;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import ru.jezemoin.article_telegrambot.articleclient.JavaGroupClient;
import ru.jezemoin.article_telegrambot.command.CommandContainer;
import ru.jezemoin.article_telegrambot.service.GroupSubService;
import ru.jezemoin.article_telegrambot.service.SendBotMessageServiceImpl;
import ru.jezemoin.article_telegrambot.service.TelegramUserService;

import static ru.jezemoin.article_telegrambot.command.CommandName.NO;


@Component
public class JavaTelegramBot implements SpringLongPollingBot, LongPollingSingleThreadUpdateConsumer {

    public static String COMMAND_PREFIX = "/";


    @Value("${bot.token}")
    private String token;

    private TelegramClient telegramClient;

    private final CommandContainer commandContainer;

    @Autowired
    public JavaTelegramBot(TelegramUserService telegramUserService, JavaGroupClient groupClient, GroupSubService groupSubService) {
        this.commandContainer = new CommandContainer(new SendBotMessageServiceImpl(this), telegramUserService,
                groupClient, groupSubService);
    }

    @PostConstruct
    public void setTelegramClient() {
        telegramClient = new OkHttpTelegramClient(getBotToken());
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return this;
    }

    @Override
    public void consume(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {

            String message_text = update.getMessage().getText().trim();
            if(message_text.startsWith(COMMAND_PREFIX)){
                String commandIdentifier = message_text.split(" ")[0].toLowerCase();

                commandContainer.retrieveCommand(commandIdentifier).execute(update);
            } else {
                commandContainer.retrieveCommand(NO.getCommandName()).execute(update);
            }
        }
    }

    public void sendAnswerMessage(SendMessage message) {
        if(message!=null) {
            try {
                telegramClient.execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}

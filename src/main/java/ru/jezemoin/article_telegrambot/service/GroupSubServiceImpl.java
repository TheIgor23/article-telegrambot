package ru.jezemoin.article_telegrambot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.jezemoin.article_telegrambot.articleclient.dto.GroupDiscussionInfo;
import ru.jezemoin.article_telegrambot.repository.entity.GroupSub;
import ru.jezemoin.article_telegrambot.repository.entity.GroupSubRepository;
import ru.jezemoin.article_telegrambot.repository.entity.TelegramUser;

import java.util.Optional;

@Service
public class GroupSubServiceImpl implements GroupSubService{
    private final GroupSubRepository groupSubRepository;
    private final TelegramUserService telegramUserService;


    @Autowired
    public GroupSubServiceImpl(GroupSubRepository groupSubRepository, TelegramUserService telegramUserService) {
        this.groupSubRepository = groupSubRepository;
        this.telegramUserService = telegramUserService;
    }


    @Override
    public GroupSub save(String chatId, GroupDiscussionInfo groupDiscussionInfo)  {
        Optional<TelegramUser> telegramUser = telegramUserService.findByChatId(chatId);
        //TODO add exception handler
        GroupSub groupSub;
        Optional<GroupSub> groupSubFromDb = groupSubRepository.findById(groupDiscussionInfo.getId());
        if(groupSubFromDb.isPresent()){
            groupSub = groupSubFromDb.get();
            Optional<TelegramUser> first = groupSub.getUsers().stream()
                    .filter(it-> it.getChatId().equalsIgnoreCase(chatId))
                    .findFirst();
            if(first.isEmpty()){
                groupSub.addUser(telegramUser.orElse(null));
            }
        } else {
            groupSub = new GroupSub();
            groupSub.addUser(telegramUser.orElse(null));
            groupSub.setId(groupDiscussionInfo.getId());
            groupSub.setTitle(groupDiscussionInfo.getTitle());
        }
        return groupSubRepository.save(groupSub);

    }
}

package ru.jezemoin.article_telegrambot.service;

import org.springframework.data.crossstore.ChangeSetPersister;
import ru.jezemoin.article_telegrambot.articleclient.dto.GroupDiscussionInfo;
import ru.jezemoin.article_telegrambot.repository.entity.GroupSub;

import java.util.Optional;

public interface GroupSubService {
    GroupSub save(String chatId, GroupDiscussionInfo groupDiscussionInfo);
    GroupSub save(GroupSub groupSub);
    Optional<GroupSub> findById(Integer id);
}

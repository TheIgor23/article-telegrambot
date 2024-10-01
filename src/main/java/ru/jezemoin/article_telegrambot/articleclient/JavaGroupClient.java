package ru.jezemoin.article_telegrambot.articleclient;

import ru.jezemoin.article_telegrambot.articleclient.dto.GroupCountRequestArgs;
import ru.jezemoin.article_telegrambot.articleclient.dto.GroupDiscussionInfo;
import ru.jezemoin.article_telegrambot.articleclient.dto.GroupInfo;
import ru.jezemoin.article_telegrambot.articleclient.dto.GroupRequestArgs;

import java.util.List;

public interface JavaGroupClient {
    List<GroupInfo> getGroupList(GroupRequestArgs requestArgs);


    List<GroupDiscussionInfo> getGroupDiscussionList(GroupRequestArgs requestArgs);


    Integer getGroupCount(GroupCountRequestArgs countRequestArgs);


    GroupDiscussionInfo getGroupById(Integer id);
}

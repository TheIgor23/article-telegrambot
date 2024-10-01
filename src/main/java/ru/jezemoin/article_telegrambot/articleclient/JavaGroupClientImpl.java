package ru.jezemoin.article_telegrambot.articleclient;

import kong.unirest.core.GenericType;
import kong.unirest.core.Unirest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.jezemoin.article_telegrambot.articleclient.dto.GroupCountRequestArgs;
import ru.jezemoin.article_telegrambot.articleclient.dto.GroupDiscussionInfo;
import ru.jezemoin.article_telegrambot.articleclient.dto.GroupInfo;
import ru.jezemoin.article_telegrambot.articleclient.dto.GroupRequestArgs;

import java.util.List;

@Component
public class JavaGroupClientImpl implements JavaGroupClient {
    private final String javaApiGroupPath;

    public JavaGroupClientImpl(@Value("${java.api.path}") String javaApi) {
        this.javaApiGroupPath = javaApi + "/groups";;
    }

    @Override
    public List<GroupInfo> getGroupList(GroupRequestArgs requestArgs) {
        return Unirest.get(javaApiGroupPath)
                .queryString(requestArgs.populateQueries())
                .asObject(new GenericType<List<GroupInfo>>() {
                })
                .getBody();
    }

    @Override
    public List<GroupDiscussionInfo> getGroupDiscussionList(GroupRequestArgs requestArgs) {
        return Unirest.get(javaApiGroupPath)
                .queryString(requestArgs.populateQueries())
                .asObject(new GenericType<List<GroupDiscussionInfo>>() {
                })
                .getBody();
    }

    @Override
    public Integer getGroupCount(GroupCountRequestArgs countRequestArgs) {
        return Integer.valueOf(
                Unirest.get(String.format("%s/count", javaApiGroupPath))
                        .queryString(countRequestArgs.populateQueries())
                        .asString()
                        .getBody()
        );
    }

    @Override
    public GroupDiscussionInfo getGroupById(Integer id) {
        return Unirest.get(String.format("%s/group%s", javaApiGroupPath, id.toString()))
                .asObject(GroupDiscussionInfo.class)
                .getBody();
    }
}

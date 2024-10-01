package ru.jezemoin.article_telegrambot.articleclient.dto;

import lombok.Data;

@Data
public class MeGroupInfo {
    private MeGroupInfoStatus status;
    private Integer userGroupId;
}

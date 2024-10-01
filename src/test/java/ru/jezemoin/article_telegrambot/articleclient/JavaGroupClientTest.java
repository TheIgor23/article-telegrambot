package ru.jezemoin.article_telegrambot.articleclient;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.jezemoin.article_telegrambot.articleclient.dto.GroupCountRequestArgs;
import ru.jezemoin.article_telegrambot.articleclient.dto.GroupDiscussionInfo;
import ru.jezemoin.article_telegrambot.articleclient.dto.GroupInfo;
import ru.jezemoin.article_telegrambot.articleclient.dto.GroupRequestArgs;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static ru.jezemoin.article_telegrambot.articleclient.dto.GroupInfoType.TECH;

@DisplayName("Integration-level testing for JavaRushGroupClientImplTest")
public class JavaGroupClientTest {
    private final JavaGroupClient groupClient = new JavaGroupClientImpl("https://javarush.com/api/1.0/rest");


    @Test
    public void shouldProperlyGetGroupsWithEmptyArgs() {
        //given
        GroupRequestArgs args = GroupRequestArgs.builder().build();

        //when
        List<GroupInfo> groupList = groupClient.getGroupList(args);

        //then
        assertNotNull(groupList);
        Assertions.assertFalse(groupList.isEmpty());
    }

    @Test
    public void shouldProperlyGetWithOffSetAndLimit() {
        //given
        GroupRequestArgs args = GroupRequestArgs.builder()
                .offset(1)
                .limit(3)
                .build();

        //when
        List<GroupInfo> groupList = groupClient.getGroupList(args);

        //then
        assertNotNull(groupList);
        assertEquals(3, groupList.size());
    }

    @Test
    public void shouldProperlyGetGroupsDiscWithEmptyArgs() {
        //given
        GroupRequestArgs args = GroupRequestArgs.builder().build();

        //when
        List<GroupDiscussionInfo> groupList = groupClient.getGroupDiscussionList(args);

        //then
        assertNotNull(groupList);
        Assertions.assertFalse(groupList.isEmpty());
    }

    @Test
    public void shouldProperlyGetGroupDiscWithOffSetAndLimit() {
        //given
        GroupRequestArgs args = GroupRequestArgs.builder()
                .offset(1)
                .limit(3)
                .build();

        //when
        List<GroupDiscussionInfo> groupList = groupClient.getGroupDiscussionList(args);

        //then
        assertNotNull(groupList);
        assertEquals(3, groupList.size());
    }

    @Test
    public void shouldProperlyGetGroupCount() {
        //given
        GroupCountRequestArgs args = GroupCountRequestArgs.builder().build();

        //when
        Integer groupCount = groupClient.getGroupCount(args);

        //then
        assertEquals(30, groupCount);
    }

    @Test
    public void shouldProperlyGetGroupTECHCount() {
        //given
        GroupCountRequestArgs args = GroupCountRequestArgs.builder()
                .type(TECH)
                .build();

        //when
        Integer groupCount = groupClient.getGroupCount(args);

        //then
        assertEquals(7, groupCount);
    }

    @Test
    public void shouldProperlyGetGroupById() {
        //given
        Integer androidGroupId = 16;

        //when
        GroupDiscussionInfo groupById = groupClient.getGroupById(androidGroupId);

        //then
        assertNotNull(groupById);
        assertEquals(16, groupById.getId());
        assertEquals(TECH, groupById.getType());
        assertEquals("android", groupById.getKey());
    }
}

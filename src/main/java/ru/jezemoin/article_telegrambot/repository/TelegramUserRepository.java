package ru.jezemoin.article_telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.jezemoin.article_telegrambot.repository.entity.TelegramUser;

import java.util.List;

public interface TelegramUserRepository extends JpaRepository<TelegramUser, String> {
    List<TelegramUser> findAllByActiveTrue();
}

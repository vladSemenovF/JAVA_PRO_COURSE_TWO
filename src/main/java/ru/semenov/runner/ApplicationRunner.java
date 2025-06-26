package ru.semenov.runner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.semenov.service.UserServiceV2;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApplicationRunner implements CommandLineRunner {
    private final UserServiceV2 service;


    @Override
    public void run(String... args) throws Exception {
        log.info("Запуск операций с пользователями...");

        var user1 = service.createUser("Владислав");
        var user2 = service.createUser("Сергей");
        var user3 = service.createUser("Владимир");

        service.getUser(user1.getId()).ifPresent(
                user -> log.info("Найден пользователь с ID {}: {}", user1.getId(), user)
        );

        log.info("ВСЕ ПОЛЬЗОВАТЕЛИ:");
        service.getAllUsers().forEach(user ->
                log.info("ПОЛЬЗОВАТЕЛЬ: {}", user)
        );

        service.deleteUser(user2.getId());

        log.info("ПОЛЬЗОВАТЕЛИ ПОСЛЕ УДАЛЕНИЯ:");
        service.getAllUsers().forEach(user ->
                log.info("ПОЛЬЗОВАТЕЛЬ: {}", user)
        );

        service.getUser(user2.getId()).ifPresentOrElse(
                user -> log.info("ПОЛЬЗОВАТЕЛЬ УДАЛЕН НО НАЙДЕН: {}", user),
                () -> log.info("ПОЛЬЗОВАТЕЛЬ С ID {} НЕ НАЙДЕН", user2.getId())
        );
    }
}

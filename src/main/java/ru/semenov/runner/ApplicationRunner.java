package ru.semenov.runner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.semenov.service.UserService;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApplicationRunner implements CommandLineRunner {
    private final UserService service;


    @Override
    public void run(String... args) throws Exception {
        log.info("Запуск операций с пользователями...");

        var user1 = service.createUser("Владислав");
        var user2 = service.createUser("Сергей");
        var user3 = service.createUser("Владимир");

        var user1FromDb = service.getUser(user1.getId());
        log.info("Найден пользователь с ID {}: {}", user1FromDb.getId(), user1FromDb);

        log.info("ВСЕ ПОЛЬЗОВАТЕЛИ:");
        service.getAllUsers().forEach(user ->
                log.info("ПОЛЬЗОВАТЕЛЬ: {}", user)
        );

        service.deleteUser(user2.getId());

        log.info("ПОЛЬЗОВАТЕЛИ ПОСЛЕ УДАЛЕНИЯ:");
        service.getAllUsers().forEach(user ->
                log.info("ПОЛЬЗОВАТЕЛЬ: {}", user)
        );

        var user2FromDb = service.getUser(user2.getId());
    }
}

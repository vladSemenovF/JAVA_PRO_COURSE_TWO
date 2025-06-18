package ru.semenov;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.semenov.config.ApplicationConfiguration;
import ru.semenov.model.User;
import ru.semenov.service.UserService;

@Slf4j
public class Main {
    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context =
                     new AnnotationConfigApplicationContext(ApplicationConfiguration.class)) {

            var userService = context.getBean(UserService.class);

            User user1 = userService.createUser("Владислав");
            User user2 = userService.createUser("Сергей");
            User user3 = userService.createUser("Владимир");

            userService.getUser(user1.getId()).ifPresent(
                    user -> log.info("Найден пользователь с ID {}: {}", user1.getId(), user)
            );

            log.info("ВСЕ ПОЛЬЗОВАТЕЛИ:");
            userService.getAllUsers().forEach(Main::getInfoUser);

            userService.deleteUser(user2.getId());

            log.info("ПОЛЬЗОВАТЕЛИ ПОСЛЕ УДАЛЕНИЯ 2 ПОЛЬЗОВАТЕЛЯ:");
            userService.getAllUsers().forEach(Main::getInfoUser);

            userService.getUser(user2.getId()).ifPresentOrElse(
                    user -> log.info("ПОЛЬЗОВАТЕЛЬ УДАЛЕН НО НАЙДЕН ОГО!: {}", user),
                    () -> log.info("ПОЛЬЗОВАТЕЛЬ КАК И ЗАДУМЫВАЛОСЬ С ID  {} НЕ НАЙДЕН!", user2.getId())
            );
        }
    }

    private static void getInfoUser(User user) {
        log.info("ПОЛЬЗОВАТЕЛЬ:  {}", user);
    }
}
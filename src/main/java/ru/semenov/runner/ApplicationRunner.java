package ru.semenov.runner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.semenov.model.Product;
import ru.semenov.model.enums.ProductType;
import ru.semenov.service.ProductService;
import ru.semenov.service.UserService;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApplicationRunner implements CommandLineRunner {
    private final UserService service;
    private final ProductService productService;


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

        log.info("Создание продуктов...");
        Product p1 = createProduct("40817810099910000001", 1000.0, ProductType.ACCOUNT, user1.getId());
        Product p2 = createProduct("40817810099910000002", 500.0, ProductType.CARD, user1.getId());
        Product p3 = createProduct("40817810099920000001", 1500.0, ProductType.ACCOUNT, user1.getId());

        log.info("Продукты пользователя {}:", user1.getId());
        productService.getProductsByUserId(user1.getId()).forEach(p ->
                log.info("Продукт: {}", p)
        );

        log.info("Поиск продукта по ID: {}", p2.getId());
        Product found = productService.getProductById(p2.getId());
        log.info("Найден продукт: {}", found);
    }

    private Product createProduct(String acc, double balance, ProductType type, Long userId) {
        Product p = new Product();
        p.setAccountNumber(acc);
        p.setBalance(balance);
        p.setProductType(type);
        p.setUserId(userId);
        return productService.createProduct(p);
    }
}

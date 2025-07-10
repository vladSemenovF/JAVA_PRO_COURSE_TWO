package ru.semenov.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.semenov.model.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByUserId(Long userId);
}
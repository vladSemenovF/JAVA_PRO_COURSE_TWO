package ru.semenov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.semenov.dao.ProductRepository;
import ru.semenov.model.Product;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Продукт не найден"));
    }

    public List<Product> getProductsByUserId(Long userId) {
        return productRepository.findByUserId(userId);
    }
}

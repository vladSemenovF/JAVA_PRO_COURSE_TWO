package ru.semenov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.semenov.dao.ProductRepository;
import ru.semenov.dto.product.response.ProductResponseDto;
import ru.semenov.model.Product;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public ProductResponseDto getProductById(Long productId) {
        return productRepository.findById(productId)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Продукт не найден"));
    }

    public List<ProductResponseDto> getProductsByUserId(Long userId) {
        return productRepository.findByUserId(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private ProductResponseDto convertToDTO(Product product) {
        var dto = new ProductResponseDto();
        dto.setId(product.getId());
        dto.setAccountNumber(product.getAccountNumber());
        dto.setBalance(product.getBalance());
        dto.setUserId(product.getUserId());
        return dto;
    }
}
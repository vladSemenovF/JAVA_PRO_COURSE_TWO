package ru.semenov.client;

import ru.semenov.dto.product.response.ProductResponseDto;

import java.util.List;

public interface ProductClient {
    List<ProductResponseDto> getProductsDtoByUserId(Long userId);
    ProductResponseDto getProductDtoById(Long productId);
}

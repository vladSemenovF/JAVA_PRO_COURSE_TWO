package ru.semenov.dto.product.response;

import lombok.Data;

@Data
public class ProductResponseDto {
    private Long id;
    private String accountNumber;
    private Double balance;
    private Long userId;
}

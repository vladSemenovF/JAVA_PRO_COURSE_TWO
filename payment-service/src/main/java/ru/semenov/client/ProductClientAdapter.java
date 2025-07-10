package ru.semenov.client;

import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;
import ru.semenov.dto.product.response.ProductResponseDto;
import ru.semenov.exception.product.ProductNotFoundException;
import ru.semenov.exception.product.ProductServiceException;

import java.time.Duration;
import java.util.List;
import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
public class ProductClientAdapter implements ProductClient {
    private final RestClient restClient;
    private final Retry productsRetry = initRetry();

    private Retry initRetry() {
        return Retry.of("product-service", RetryConfig.custom()
                .maxAttempts(3)
                .waitDuration(Duration.ofSeconds(2))
                .retryOnException(e -> e instanceof HttpServerErrorException)
                .build());
    }
    @Override
    public List<ProductResponseDto> getProductsDtoByUserId(Long userId) {
        Supplier<List<ProductResponseDto>> supplier = () -> {
            try {
                return restClient.get()
                        .uri("/api/products/user/{userId}", userId)
                        .retrieve()
                        .body(new ParameterizedTypeReference<>() {
                        });
            } catch (Exception e) {
                throw new ProductServiceException("Failed to fetch products for user: " + userId, e);
            }
        };

        return productsRetry.executeSupplier(supplier);
    }

    @Override
    public ProductResponseDto getProductDtoById(Long productId) {
        Supplier<ProductResponseDto> supplier = () -> {
            try {
                return restClient.get()
                        .uri("/api/products/{productId}", productId)
                        .retrieve()
                        .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                            if (res.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(404))) {
                                throw new ProductNotFoundException("Продукт не найден: " + productId);
                            }
                            throw new ProductServiceException("Ошибка клиента: " + res.getStatusCode());
                        })
                        .onStatus(HttpStatusCode::is5xxServerError, (req, res) -> {
                            throw new ProductServiceException("Ошибка сервера: " + res.getStatusCode());
                        })
                        .body(ProductResponseDto.class);
            } catch (ProductNotFoundException ex) {
                throw ex;
            } catch (Exception e) {
                throw new ProductServiceException("Ошибка при получении продукта: " + productId, e);
            }
        };

        return productsRetry.executeSupplier(supplier);
    }

}

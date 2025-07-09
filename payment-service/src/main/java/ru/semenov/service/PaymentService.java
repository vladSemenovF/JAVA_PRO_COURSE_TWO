package ru.semenov.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.semenov.client.ProductClient;
import ru.semenov.dto.payment.request.PayRequest;
import ru.semenov.dto.payment.request.PayResponse;
import ru.semenov.exception.payment.PaymentException;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final ProductClient productClient;

    public PayResponse processPayment(PayRequest request) {
        var product = productClient.getProductDtoById(request.getProductId());

        if (isNull(product)) {
            throw new PaymentException("Продукт не найден");
        }

        if (product.getBalance() < request.getAmount()) {
            log.warn("Недостаточно средств на продукте {}: баланс {}, запрошено {}",
                    request.getProductId(), product.getBalance(), request.getAmount());
            throw new PaymentException("Недостаточно средств на продукте");
        }

        return new PayResponse("SUCCESS", "Платёж выполнен успешно");
    }
}
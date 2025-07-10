package ru.semenov.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.semenov.dto.payment.request.PayRequest;
import ru.semenov.dto.payment.request.PayResponse;
import ru.semenov.service.PaymentService;

@Component
@RequiredArgsConstructor
public class PaymentFacade {

    private final PaymentService paymentService;

    public PayResponse executePayment(PayRequest request) {
        return paymentService.processPayment(request);
    }
}

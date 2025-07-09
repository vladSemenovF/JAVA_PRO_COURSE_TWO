package ru.semenov.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.semenov.dto.payment.request.PayRequest;
import ru.semenov.dto.payment.request.PayResponse;
import ru.semenov.facade.PaymentFacade;

@RestController
@RequestMapping("/api/payments")
@Tag(name = "Платежи", description = "API для работы с платежами")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentFacade facade;

    @PostMapping("/pay")
    @Operation(summary = "Выполнить платёж")
    @ApiResponse(responseCode = "200", description = "Успех",
            content = @Content(schema = @Schema(implementation = PayResponse.class)))
    public ResponseEntity<PayResponse> pay(@RequestBody PayRequest request) {
        return ResponseEntity.ok(facade.executePayment(request));
    }
}
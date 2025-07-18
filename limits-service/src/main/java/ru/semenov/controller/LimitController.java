package ru.semenov.controller;

import jakarta.validation.constraints.DecimalMin;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.semenov.service.LimitService;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/limits")
@RequiredArgsConstructor
@Validated
public class LimitController {
    private final LimitService limitService;

    @GetMapping("/{userId}")
    public ResponseEntity<BigDecimal> getRemainingLimit(@PathVariable Long userId) {
        return ResponseEntity.ok(limitService.getUserLimit(userId).getRemainingLimit());
    }

    @PostMapping("/{userId}/decrease")
    public ResponseEntity<Void> decreaseLimit(
            @PathVariable Long userId,
            @RequestParam @DecimalMin("0.01") BigDecimal amount
    ) {
        limitService.decreaseLimit(userId, amount);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{userId}/restore")
    public ResponseEntity<Void> restoreLimit(
            @PathVariable Long userId,
            @RequestParam @DecimalMin("0.01") BigDecimal amount
    ) {
        limitService.restoreLimit(userId, amount);
        return ResponseEntity.ok().build();
    }
}

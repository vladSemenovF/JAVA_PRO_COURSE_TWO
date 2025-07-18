package ru.semenov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.semenov.dao.UserLimitRepository;
import ru.semenov.model.exception.InsufficientLimitException;
import ru.semenov.model.entity.UserLimit;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class LimitService {
    private final UserLimitRepository userLimitRepository;
    private static final BigDecimal DEFAULT_LIMIT = new BigDecimal("10000.00");

    @Transactional
    public UserLimit getUserLimit(Long userId) {
        return userLimitRepository.findByUserId(userId)
                .orElseGet(() -> createUserLimit(userId));
    }

    private UserLimit createUserLimit(Long userId) {
        var newLimit = new UserLimit();
        newLimit.setUserId(userId);
        return userLimitRepository.save(newLimit);
    }

    @Transactional
    @Retryable(value = ObjectOptimisticLockingFailureException.class, maxAttempts = 4)
    public void decreaseLimit(Long userId, BigDecimal amount) {
        var limit = getUserLimit(userId);
        var newRemaining = limit.getRemainingLimit().subtract(amount);

        if (newRemaining.compareTo(BigDecimal.ZERO) < 0) {
            throw new InsufficientLimitException("Лимит исчерпан");
        }

        limit.setRemainingLimit(newRemaining);
        userLimitRepository.save(limit);
    }

    @Transactional
    @Retryable(value = ObjectOptimisticLockingFailureException.class, maxAttempts = 4)
    public void restoreLimit(Long userId, BigDecimal amount) {
        var limit = getUserLimit(userId);
        var newRemaining = limit.getRemainingLimit().add(amount);

        if (newRemaining.compareTo(limit.getDailyLimit()) > 0) {
            newRemaining = limit.getDailyLimit();
        }

        limit.setRemainingLimit(newRemaining);
        userLimitRepository.save(limit);
    }
}

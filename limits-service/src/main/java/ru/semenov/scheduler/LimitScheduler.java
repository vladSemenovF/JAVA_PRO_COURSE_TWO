package ru.semenov.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.semenov.dao.UserLimitRepository;

@Component
@RequiredArgsConstructor
public class LimitScheduler {
    private final UserLimitRepository repository;

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void resetDailyLimits() {
        repository.resetAllLimits();
    }
}

package ru.semenov.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.semenov.model.entity.UserLimit;

import java.util.Optional;

public interface UserLimitRepository extends JpaRepository<UserLimit, Long> {
    Optional<UserLimit> findByUserId(Long userId);

    @Modifying
    @Query("UPDATE UserLimit SET remainingLimit = dailyLimit, version = version + 1")
    void resetAllLimits();
}

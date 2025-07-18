package ru.semenov.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

import java.math.BigDecimal;

@Entity
@Table(name = UserLimit.USER_LIMITS)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Audited
public class UserLimit {
    public static final String USER_LIMITS="user_limits";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", unique = true, nullable = false)
    private Long userId;

    @Column(name = "daily_limit", precision = 19, scale = 2, nullable = false)
    private BigDecimal dailyLimit = new BigDecimal("10000.00");

    @Column(name = "remaining_limit", precision = 19, scale = 2, nullable = false)
    private BigDecimal remainingLimit = new BigDecimal("10000.00");

    @Version
    private Long version;
}

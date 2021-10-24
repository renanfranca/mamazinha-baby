package com.mamazinha.baby.repository;

import com.mamazinha.baby.domain.HumorHistory;
import java.time.ZonedDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the HumorHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HumorHistoryRepository extends JpaRepository<HumorHistory, Long> {
    boolean existsByBabyProfileId(Long id);

    boolean existsByBabyProfileIdAndBabyProfileUserId(Long id, String userId);

    List<HumorHistory> findByBabyProfileIdAndDateGreaterThanEqualAndDateLessThan(
        Long id,
        ZonedDateTime todayMidnight,
        ZonedDateTime tomorrowMidnight
    );
}

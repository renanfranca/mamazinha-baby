package com.mamazinha.baby.repository;

import com.mamazinha.baby.domain.HumorHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the HumorHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HumorHistoryRepository extends JpaRepository<HumorHistory, Long> {}

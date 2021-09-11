package com.mamazinha.babyprofile.repository;

import com.mamazinha.babyprofile.domain.HumorHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the HumorHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HumorHistoryRepository extends JpaRepository<HumorHistory, Long> {}

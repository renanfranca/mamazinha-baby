package com.mamazinha.baby.repository;

import com.mamazinha.baby.domain.BreastFeed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the BreastFeed entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BreastFeedRepository extends JpaRepository<BreastFeed, Long> {
    Page<BreastFeed> findByBabyProfileUserId(Pageable pageable, String string);
}

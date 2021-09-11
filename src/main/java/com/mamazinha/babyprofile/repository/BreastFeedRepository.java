package com.mamazinha.babyprofile.repository;

import com.mamazinha.babyprofile.domain.BreastFeed;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the BreastFeed entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BreastFeedRepository extends JpaRepository<BreastFeed, Long> {}

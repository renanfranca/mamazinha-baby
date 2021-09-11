package com.mamazinha.babyprofile.repository;

import com.mamazinha.babyprofile.domain.BabyProfile;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the BabyProfile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BabyProfileRepository extends JpaRepository<BabyProfile, Long> {}

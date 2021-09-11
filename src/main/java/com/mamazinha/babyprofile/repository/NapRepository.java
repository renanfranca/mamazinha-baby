package com.mamazinha.babyprofile.repository;

import com.mamazinha.babyprofile.domain.Nap;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Nap entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NapRepository extends JpaRepository<Nap, Long> {}

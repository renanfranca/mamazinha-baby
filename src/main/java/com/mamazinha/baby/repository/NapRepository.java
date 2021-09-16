package com.mamazinha.baby.repository;

import com.mamazinha.baby.domain.Nap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Nap entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NapRepository extends JpaRepository<Nap, Long> {
    Page<Nap> findByBabyProfileUserId(Pageable pageable, String string);
}

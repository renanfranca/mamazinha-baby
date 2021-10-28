package com.mamazinha.baby.repository;

import com.mamazinha.baby.domain.Height;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Height entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HeightRepository extends JpaRepository<Height, Long> {
    Page<Height> findByBabyProfileUserId(Pageable pageable, String string);
}

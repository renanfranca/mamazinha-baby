package com.mamazinha.baby.repository;

import com.mamazinha.baby.domain.Height;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Height entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HeightRepository extends JpaRepository<Height, Long> {}

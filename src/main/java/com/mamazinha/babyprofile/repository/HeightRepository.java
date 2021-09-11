package com.mamazinha.babyprofile.repository;

import com.mamazinha.babyprofile.domain.Height;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Height entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HeightRepository extends JpaRepository<Height, Long> {}

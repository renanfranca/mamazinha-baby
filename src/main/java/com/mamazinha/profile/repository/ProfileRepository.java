package com.mamazinha.profile.repository;

import com.mamazinha.profile.domain.Profile;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Profile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    @Query("select profile from Profile profile where profile.user.login = ?#{principal.preferredUsername}")
    List<Profile> findByUserIsCurrentUser();
}

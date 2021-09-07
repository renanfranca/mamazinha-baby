package com.mamazinha.profile.service.dto;

import com.mamazinha.profile.domain.enumeration.Pain;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.mamazinha.profile.domain.BreastFeed} entity.
 */
public class BreastFeedDTO implements Serializable {

    private Long id;

    private ZonedDateTime start;

    private ZonedDateTime end;

    private Pain pain;

    private ProfileDTO profile;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getStart() {
        return start;
    }

    public void setStart(ZonedDateTime start) {
        this.start = start;
    }

    public ZonedDateTime getEnd() {
        return end;
    }

    public void setEnd(ZonedDateTime end) {
        this.end = end;
    }

    public Pain getPain() {
        return pain;
    }

    public void setPain(Pain pain) {
        this.pain = pain;
    }

    public ProfileDTO getProfile() {
        return profile;
    }

    public void setProfile(ProfileDTO profile) {
        this.profile = profile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BreastFeedDTO)) {
            return false;
        }

        BreastFeedDTO breastFeedDTO = (BreastFeedDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, breastFeedDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BreastFeedDTO{" +
            "id=" + getId() +
            ", start='" + getStart() + "'" +
            ", end='" + getEnd() + "'" +
            ", pain='" + getPain() + "'" +
            ", profile=" + getProfile() +
            "}";
    }
}

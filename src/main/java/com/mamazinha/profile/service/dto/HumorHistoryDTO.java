package com.mamazinha.profile.service.dto;

import com.mamazinha.profile.domain.enumeration.Humor;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.mamazinha.profile.domain.HumorHistory} entity.
 */
public class HumorHistoryDTO implements Serializable {

    private Long id;

    private Humor humor;

    private ZonedDateTime date;

    private ProfileDTO profile;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Humor getHumor() {
        return humor;
    }

    public void setHumor(Humor humor) {
        this.humor = humor;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
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
        if (!(o instanceof HumorHistoryDTO)) {
            return false;
        }

        HumorHistoryDTO humorHistoryDTO = (HumorHistoryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, humorHistoryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HumorHistoryDTO{" +
            "id=" + getId() +
            ", humor='" + getHumor() + "'" +
            ", date='" + getDate() + "'" +
            ", profile=" + getProfile() +
            "}";
    }
}

package com.mamazinha.profile.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.mamazinha.profile.domain.Height} entity.
 */
public class HeightDTO implements Serializable {

    private Long id;

    private Float value;

    private LocalDate date;

    private Float idealWight;

    private ProfileDTO profile;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Float getIdealWight() {
        return idealWight;
    }

    public void setIdealWight(Float idealWight) {
        this.idealWight = idealWight;
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
        if (!(o instanceof HeightDTO)) {
            return false;
        }

        HeightDTO heightDTO = (HeightDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, heightDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HeightDTO{" +
            "id=" + getId() +
            ", value=" + getValue() +
            ", date='" + getDate() + "'" +
            ", idealWight=" + getIdealWight() +
            ", profile=" + getProfile() +
            "}";
    }
}

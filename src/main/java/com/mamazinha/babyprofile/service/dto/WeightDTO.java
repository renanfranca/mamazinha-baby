package com.mamazinha.babyprofile.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.mamazinha.babyprofile.domain.Weight} entity.
 */
public class WeightDTO implements Serializable {

    private Long id;

    private Float value;

    private LocalDate date;

    private Float idealWight;

    private BabyProfileDTO babyProfile;

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

    public BabyProfileDTO getBabyProfile() {
        return babyProfile;
    }

    public void setBabyProfile(BabyProfileDTO babyProfile) {
        this.babyProfile = babyProfile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WeightDTO)) {
            return false;
        }

        WeightDTO weightDTO = (WeightDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, weightDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WeightDTO{" +
            "id=" + getId() +
            ", value=" + getValue() +
            ", date='" + getDate() + "'" +
            ", idealWight=" + getIdealWight() +
            ", babyProfile=" + getBabyProfile() +
            "}";
    }
}

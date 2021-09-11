package com.mamazinha.babyprofile.service.dto;

import com.mamazinha.babyprofile.domain.enumeration.Humor;
import com.mamazinha.babyprofile.domain.enumeration.Place;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link com.mamazinha.babyprofile.domain.Nap} entity.
 */
public class NapDTO implements Serializable {

    private Long id;

    private ZonedDateTime start;

    private ZonedDateTime end;

    private Humor humor;

    private Place place;

    private BabyProfileDTO babyProfile;

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

    public Humor getHumor() {
        return humor;
    }

    public void setHumor(Humor humor) {
        this.humor = humor;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
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
        if (!(o instanceof NapDTO)) {
            return false;
        }

        NapDTO napDTO = (NapDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, napDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NapDTO{" +
            "id=" + getId() +
            ", start='" + getStart() + "'" +
            ", end='" + getEnd() + "'" +
            ", humor='" + getHumor() + "'" +
            ", place='" + getPlace() + "'" +
            ", babyProfile=" + getBabyProfile() +
            "}";
    }
}

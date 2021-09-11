package com.mamazinha.babyprofile.domain;

import com.mamazinha.babyprofile.domain.enumeration.Humor;
import com.mamazinha.babyprofile.domain.enumeration.Place;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Nap.
 */
@Entity
@Table(name = "nap")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Nap implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "start")
    private ZonedDateTime start;

    @Column(name = "jhi_end")
    private ZonedDateTime end;

    @Enumerated(EnumType.STRING)
    @Column(name = "humor")
    private Humor humor;

    @Enumerated(EnumType.STRING)
    @Column(name = "place")
    private Place place;

    @ManyToOne
    private BabyProfile babyProfile;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Nap id(Long id) {
        this.id = id;
        return this;
    }

    public ZonedDateTime getStart() {
        return this.start;
    }

    public Nap start(ZonedDateTime start) {
        this.start = start;
        return this;
    }

    public void setStart(ZonedDateTime start) {
        this.start = start;
    }

    public ZonedDateTime getEnd() {
        return this.end;
    }

    public Nap end(ZonedDateTime end) {
        this.end = end;
        return this;
    }

    public void setEnd(ZonedDateTime end) {
        this.end = end;
    }

    public Humor getHumor() {
        return this.humor;
    }

    public Nap humor(Humor humor) {
        this.humor = humor;
        return this;
    }

    public void setHumor(Humor humor) {
        this.humor = humor;
    }

    public Place getPlace() {
        return this.place;
    }

    public Nap place(Place place) {
        this.place = place;
        return this;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public BabyProfile getBabyProfile() {
        return this.babyProfile;
    }

    public Nap babyProfile(BabyProfile babyProfile) {
        this.setBabyProfile(babyProfile);
        return this;
    }

    public void setBabyProfile(BabyProfile babyProfile) {
        this.babyProfile = babyProfile;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Nap)) {
            return false;
        }
        return id != null && id.equals(((Nap) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Nap{" +
            "id=" + getId() +
            ", start='" + getStart() + "'" +
            ", end='" + getEnd() + "'" +
            ", humor='" + getHumor() + "'" +
            ", place='" + getPlace() + "'" +
            "}";
    }
}

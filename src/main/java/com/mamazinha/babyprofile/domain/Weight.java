package com.mamazinha.babyprofile.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Weight.
 */
@Entity
@Table(name = "weight")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Weight implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "value")
    private Float value;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "ideal_wight")
    private Float idealWight;

    @ManyToOne
    private BabyProfile babyProfile;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Weight id(Long id) {
        this.id = id;
        return this;
    }

    public Float getValue() {
        return this.value;
    }

    public Weight value(Float value) {
        this.value = value;
        return this;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public Weight date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Float getIdealWight() {
        return this.idealWight;
    }

    public Weight idealWight(Float idealWight) {
        this.idealWight = idealWight;
        return this;
    }

    public void setIdealWight(Float idealWight) {
        this.idealWight = idealWight;
    }

    public BabyProfile getBabyProfile() {
        return this.babyProfile;
    }

    public Weight babyProfile(BabyProfile babyProfile) {
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
        if (!(o instanceof Weight)) {
            return false;
        }
        return id != null && id.equals(((Weight) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Weight{" +
            "id=" + getId() +
            ", value=" + getValue() +
            ", date='" + getDate() + "'" +
            ", idealWight=" + getIdealWight() +
            "}";
    }
}

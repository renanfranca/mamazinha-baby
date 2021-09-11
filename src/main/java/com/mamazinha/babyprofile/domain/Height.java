package com.mamazinha.babyprofile.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Height.
 */
@Entity
@Table(name = "height")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Height implements Serializable {

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

    public Height id(Long id) {
        this.id = id;
        return this;
    }

    public Float getValue() {
        return this.value;
    }

    public Height value(Float value) {
        this.value = value;
        return this;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public Height date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Float getIdealWight() {
        return this.idealWight;
    }

    public Height idealWight(Float idealWight) {
        this.idealWight = idealWight;
        return this;
    }

    public void setIdealWight(Float idealWight) {
        this.idealWight = idealWight;
    }

    public BabyProfile getBabyProfile() {
        return this.babyProfile;
    }

    public Height babyProfile(BabyProfile babyProfile) {
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
        if (!(o instanceof Height)) {
            return false;
        }
        return id != null && id.equals(((Height) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Height{" +
            "id=" + getId() +
            ", value=" + getValue() +
            ", date='" + getDate() + "'" +
            ", idealWight=" + getIdealWight() +
            "}";
    }
}

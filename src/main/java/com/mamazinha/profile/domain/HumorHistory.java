package com.mamazinha.profile.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mamazinha.profile.domain.enumeration.Humor;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A HumorHistory.
 */
@Entity
@Table(name = "humor_history")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class HumorHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "humor")
    private Humor humor;

    @Column(name = "date")
    private ZonedDateTime date;

    @ManyToOne
    @JsonIgnoreProperties(value = { "user" }, allowSetters = true)
    private Profile profile;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public HumorHistory id(Long id) {
        this.id = id;
        return this;
    }

    public Humor getHumor() {
        return this.humor;
    }

    public HumorHistory humor(Humor humor) {
        this.humor = humor;
        return this;
    }

    public void setHumor(Humor humor) {
        this.humor = humor;
    }

    public ZonedDateTime getDate() {
        return this.date;
    }

    public HumorHistory date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Profile getProfile() {
        return this.profile;
    }

    public HumorHistory profile(Profile profile) {
        this.setProfile(profile);
        return this;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HumorHistory)) {
            return false;
        }
        return id != null && id.equals(((HumorHistory) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HumorHistory{" +
            "id=" + getId() +
            ", humor='" + getHumor() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}

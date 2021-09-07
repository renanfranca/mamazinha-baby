package com.mamazinha.profile.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mamazinha.profile.domain.enumeration.Pain;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A BreastFeed.
 */
@Entity
@Table(name = "breast_feed")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BreastFeed implements Serializable {

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
    @Column(name = "pain")
    private Pain pain;

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

    public BreastFeed id(Long id) {
        this.id = id;
        return this;
    }

    public ZonedDateTime getStart() {
        return this.start;
    }

    public BreastFeed start(ZonedDateTime start) {
        this.start = start;
        return this;
    }

    public void setStart(ZonedDateTime start) {
        this.start = start;
    }

    public ZonedDateTime getEnd() {
        return this.end;
    }

    public BreastFeed end(ZonedDateTime end) {
        this.end = end;
        return this;
    }

    public void setEnd(ZonedDateTime end) {
        this.end = end;
    }

    public Pain getPain() {
        return this.pain;
    }

    public BreastFeed pain(Pain pain) {
        this.pain = pain;
        return this;
    }

    public void setPain(Pain pain) {
        this.pain = pain;
    }

    public Profile getProfile() {
        return this.profile;
    }

    public BreastFeed profile(Profile profile) {
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
        if (!(o instanceof BreastFeed)) {
            return false;
        }
        return id != null && id.equals(((BreastFeed) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BreastFeed{" +
            "id=" + getId() +
            ", start='" + getStart() + "'" +
            ", end='" + getEnd() + "'" +
            ", pain='" + getPain() + "'" +
            "}";
    }
}

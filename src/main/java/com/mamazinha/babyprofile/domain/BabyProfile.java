package com.mamazinha.babyprofile.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A BabyProfile.
 */
@Entity
@Table(name = "baby_profile")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BabyProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Lob
    @Column(name = "picture")
    private byte[] picture;

    @Column(name = "picture_content_type")
    private String pictureContentType;

    @Column(name = "birthday")
    private ZonedDateTime birthday;

    @Column(name = "sign")
    private String sign;

    @Column(name = "user_id")
    private String userId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BabyProfile id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public BabyProfile name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getPicture() {
        return this.picture;
    }

    public BabyProfile picture(byte[] picture) {
        this.picture = picture;
        return this;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getPictureContentType() {
        return this.pictureContentType;
    }

    public BabyProfile pictureContentType(String pictureContentType) {
        this.pictureContentType = pictureContentType;
        return this;
    }

    public void setPictureContentType(String pictureContentType) {
        this.pictureContentType = pictureContentType;
    }

    public ZonedDateTime getBirthday() {
        return this.birthday;
    }

    public BabyProfile birthday(ZonedDateTime birthday) {
        this.birthday = birthday;
        return this;
    }

    public void setBirthday(ZonedDateTime birthday) {
        this.birthday = birthday;
    }

    public String getSign() {
        return this.sign;
    }

    public BabyProfile sign(String sign) {
        this.sign = sign;
        return this;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getUserId() {
        return this.userId;
    }

    public BabyProfile userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BabyProfile)) {
            return false;
        }
        return id != null && id.equals(((BabyProfile) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BabyProfile{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", picture='" + getPicture() + "'" +
            ", pictureContentType='" + getPictureContentType() + "'" +
            ", birthday='" + getBirthday() + "'" +
            ", sign='" + getSign() + "'" +
            ", userId='" + getUserId() + "'" +
            "}";
    }
}

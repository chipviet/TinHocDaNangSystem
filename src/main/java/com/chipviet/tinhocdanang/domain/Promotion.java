package com.chipviet.tinhocdanang.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Promotion.
 */
@Entity
@Table(name = "promotion")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Promotion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "desciption")
    private String desciption;

    @Column(name = "link_to_promotional_products")
    private String linkToPromotionalProducts;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDesciption() {
        return desciption;
    }

    public Promotion desciption(String desciption) {
        this.desciption = desciption;
        return this;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }

    public String getLinkToPromotionalProducts() {
        return linkToPromotionalProducts;
    }

    public Promotion linkToPromotionalProducts(String linkToPromotionalProducts) {
        this.linkToPromotionalProducts = linkToPromotionalProducts;
        return this;
    }

    public void setLinkToPromotionalProducts(String linkToPromotionalProducts) {
        this.linkToPromotionalProducts = linkToPromotionalProducts;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Promotion)) {
            return false;
        }
        return id != null && id.equals(((Promotion) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Promotion{" +
            "id=" + getId() +
            ", desciption='" + getDesciption() + "'" +
            ", linkToPromotionalProducts='" + getLinkToPromotionalProducts() + "'" +
            "}";
    }
}

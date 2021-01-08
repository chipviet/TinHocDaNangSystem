package com.chipviet.tinhocdanang.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A PromotionProduction.
 */
@Entity
@Table(name = "promotion_production")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PromotionProduction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "promotionProductions", allowSetters = true)
    private Production production;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "promotionProductions", allowSetters = true)
    private Promotion promotion;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Production getProduction() {
        return production;
    }

    public PromotionProduction production(Production production) {
        this.production = production;
        return this;
    }

    public void setProduction(Production production) {
        this.production = production;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public PromotionProduction promotion(Promotion promotion) {
        this.promotion = promotion;
        return this;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PromotionProduction)) {
            return false;
        }
        return id != null && id.equals(((PromotionProduction) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PromotionProduction{" +
            "id=" + getId() +
            "}";
    }
}

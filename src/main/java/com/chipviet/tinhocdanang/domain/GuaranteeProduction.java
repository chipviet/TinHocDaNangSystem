package com.chipviet.tinhocdanang.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A GuaranteeProduction.
 */
@Entity
@Table(name = "guarantee_production")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class GuaranteeProduction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "guaranteeProductions", allowSetters = true)
    private Production production;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "guaranteeProductions", allowSetters = true)
    private Guarantee guarantee;

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

    public GuaranteeProduction production(Production production) {
        this.production = production;
        return this;
    }

    public void setProduction(Production production) {
        this.production = production;
    }

    public Guarantee getGuarantee() {
        return guarantee;
    }

    public GuaranteeProduction guarantee(Guarantee guarantee) {
        this.guarantee = guarantee;
        return this;
    }

    public void setGuarantee(Guarantee guarantee) {
        this.guarantee = guarantee;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GuaranteeProduction)) {
            return false;
        }
        return id != null && id.equals(((GuaranteeProduction) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GuaranteeProduction{" +
            "id=" + getId() +
            "}";
    }
}

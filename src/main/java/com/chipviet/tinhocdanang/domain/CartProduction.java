package com.chipviet.tinhocdanang.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A CartProduction.
 */
@Entity
@Table(name = "cart_production")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CartProduction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Column(name = "quanlity")
    private Long quanlity;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "cartProductions", allowSetters = true)
    private Cart cart;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "cartProductions", allowSetters = true)
    private Production prodution;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public CartProduction creationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Long getQuanlity() {
        return quanlity;
    }

    public CartProduction quanlity(Long quanlity) {
        this.quanlity = quanlity;
        return this;
    }

    public void setQuanlity(Long quanlity) {
        this.quanlity = quanlity;
    }

    public Cart getCart() {
        return cart;
    }

    public CartProduction cart(Cart cart) {
        this.cart = cart;
        return this;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Production getProdution() {
        return prodution;
    }

    public CartProduction prodution(Production production) {
        this.prodution = production;
        return this;
    }

    public void setProdution(Production production) {
        this.prodution = production;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CartProduction)) {
            return false;
        }
        return id != null && id.equals(((CartProduction) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CartProduction{" +
            "id=" + getId() +
            ", creationDate='" + getCreationDate() + "'" +
            ", quanlity=" + getQuanlity() +
            "}";
    }
}

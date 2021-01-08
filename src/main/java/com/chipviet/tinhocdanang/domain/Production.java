package com.chipviet.tinhocdanang.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Production.
 */
@Entity
@Table(name = "production")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Production implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "price", nullable = false)
    private Long price;

    @Column(name = "description")
    private String description;

    @Column(name = "image_url")
    private String imageURL;

    @Column(name = "sale_price")
    private Long salePrice;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "jhi_condition")
    private Double condition;

    @Column(name = "origin")
    private String origin;

    @Column(name = "configuration")
    private String configuration;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "productions", allowSetters = true)
    private Brand brand;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "productions", allowSetters = true)
    private Category category;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Production name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrice() {
        return price;
    }

    public Production price(Long price) {
        this.price = price;
        return this;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public Production description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public Production imageURL(String imageURL) {
        this.imageURL = imageURL;
        return this;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public Long getSalePrice() {
        return salePrice;
    }

    public Production salePrice(Long salePrice) {
        this.salePrice = salePrice;
        return this;
    }

    public void setSalePrice(Long salePrice) {
        this.salePrice = salePrice;
    }

    public Long getQuantity() {
        return quantity;
    }

    public Production quantity(Long quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Double getCondition() {
        return condition;
    }

    public Production condition(Double condition) {
        this.condition = condition;
        return this;
    }

    public void setCondition(Double condition) {
        this.condition = condition;
    }

    public String getOrigin() {
        return origin;
    }

    public Production origin(String origin) {
        this.origin = origin;
        return this;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getConfiguration() {
        return configuration;
    }

    public Production configuration(String configuration) {
        this.configuration = configuration;
        return this;
    }

    public void setConfiguration(String configuration) {
        this.configuration = configuration;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Production creationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Brand getBrand() {
        return brand;
    }

    public Production brand(Brand brand) {
        this.brand = brand;
        return this;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Category getCategory() {
        return category;
    }

    public Production category(Category category) {
        this.category = category;
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Production)) {
            return false;
        }
        return id != null && id.equals(((Production) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Production{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", price=" + getPrice() +
            ", description='" + getDescription() + "'" +
            ", imageURL='" + getImageURL() + "'" +
            ", salePrice=" + getSalePrice() +
            ", quantity=" + getQuantity() +
            ", condition=" + getCondition() +
            ", origin='" + getOrigin() + "'" +
            ", configuration='" + getConfiguration() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            "}";
    }
}

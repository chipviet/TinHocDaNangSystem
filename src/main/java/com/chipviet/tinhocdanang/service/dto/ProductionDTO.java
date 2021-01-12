package com.chipviet.tinhocdanang.service.dto;

import com.chipviet.tinhocdanang.domain.Brand;
import com.chipviet.tinhocdanang.domain.Category;
import com.chipviet.tinhocdanang.domain.Production;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.time.LocalDate;

public class ProductionDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private Long price;

    private String description;


    private Long salePrice;

    private Long quantity;

    private Double condition;

    private String origin;

    private LocalDate creationDate;

    private Brand brand;

    private Category category;

    public ProductionDTO(){

    }

    public ProductionDTO(Production production){
        this.id = production.getId();
        this.name = production.getName();
        this.price = production.getPrice();
        this.description = production.getDescription();
        this.salePrice = production.getSalePrice();
        this.quantity = production.getQuantity();
        this.condition = production.getCondition();
        this.origin = production.getOrigin();
        this.creationDate = production.getCreationDate();
        this.brand = production.getBrand();
        this.category = production.getCategory();
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Long salePrice) {
        this.salePrice = salePrice;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Double getCondition() {
        return condition;
    }

    public void setCondition(Double condition) {
        this.condition = condition;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductionDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", price=" + getPrice() +
            ", description='" + getDescription() + "'" +
            ", salePrice=" + getSalePrice() +
            ", quantity=" + getQuantity() +
            ", condition=" + getCondition() +
            ", origin='" + getOrigin() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            "}";
    }
}

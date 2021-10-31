package com.epam.esm.entity;

import org.springframework.hateoas.RepresentationModel;

import java.util.Date;
import java.util.Objects;

public class Order extends RepresentationModel<Order> {

    private Long id;

    private Date purchaseTimestamp;

    private Integer totalCost;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getPurchaseTimestamp() {
        return purchaseTimestamp;
    }

    public void setPurchaseTimestamp(Date purchaseTimestamp) {
        this.purchaseTimestamp = purchaseTimestamp;
    }

    public Integer getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Integer totalCost) {
        this.totalCost = totalCost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(purchaseTimestamp, order.purchaseTimestamp)
                && Objects.equals(totalCost, order.totalCost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, purchaseTimestamp, totalCost);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", purchaseTimestamp=" + purchaseTimestamp +
                ", totalCost=" + totalCost +
                '}';
    }

}

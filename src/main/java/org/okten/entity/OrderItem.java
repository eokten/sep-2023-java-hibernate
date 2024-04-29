package org.okten.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "order_items")
public class OrderItem {

    @EmbeddedId
    @ToString.Exclude
    private OrderItemId id;

    private Integer quantity;

    private String comment;
}

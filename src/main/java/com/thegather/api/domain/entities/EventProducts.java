package com.thegather.api.domain.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "EVENTS_PRODUCTS")
public class EventProducts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "EVENT_ID")
    private Long event_id;

    @Column(nullable = false, name = "PRODUCT_ID")
    private Long product_id;

    public EventProducts() {
    }

    public EventProducts(Long id, Long event_id, Long product_id) {
        this.id = id;
        this.event_id = event_id;
        this.product_id = product_id;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEvent_id() {
        return event_id;
    }

    public void setEvent_id(Long event_id) {
        this.event_id = event_id;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }
}


package com.inditex.core.infrastructure.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

import java.io.Serializable;

@Entity
@Table(name = "currencies")
public class CurrencyEntity implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

}

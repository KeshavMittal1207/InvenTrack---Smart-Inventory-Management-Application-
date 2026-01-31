package com.smartinventorymanagement.Inventory_Service.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Item implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String itemId;
    private String name;
    private String category;

    private int quantity;
    private int thresholdQuantity;
    private Long inventoryId;
    @Temporal(TemporalType.DATE)
    private Date expiryDate;
}

package com.smartinventorymanagement.Inventory_Service.Model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryEntry implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inventoryId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "inventoryId", referencedColumnName = "inventoryId", insertable = false, updatable = false)
    @Builder.Default
    private List<Item> items = new ArrayList<>();
    private String sellerId;

    @Temporal(TemporalType.DATE)
    private Date addedOn;

}


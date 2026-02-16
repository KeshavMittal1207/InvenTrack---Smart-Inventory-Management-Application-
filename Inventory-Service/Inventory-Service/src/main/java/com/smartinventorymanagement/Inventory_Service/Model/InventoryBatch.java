package com.smartinventorymanagement.Inventory_Service.Model;


import java.util.Date;

import com.smartinventorymanagement.Inventory_Service.Enums.BatchStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "inventory_batches",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"product_id","batchNo"})
    }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryBatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long batchId;

    @Column(name="product_id" , nullable =false)
    private Long productId;

    @Column(nullable = false)
    private String batchNo;

    private int quantity;

    private int thresholdQuantity;

    @Temporal(TemporalType.DATE)
    private Date expiryDate;

    @Enumerated(EnumType.STRING)
    private BatchStatus status;

    @Column(nullable = false)
    private String sellerId;
}

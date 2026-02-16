package com.smartinventorymanagement.Inventory_Service.Model;


import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "stock_summary")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockSummary {

    @Id
    private Long productId;
    private int totalQuantity;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdated;
}
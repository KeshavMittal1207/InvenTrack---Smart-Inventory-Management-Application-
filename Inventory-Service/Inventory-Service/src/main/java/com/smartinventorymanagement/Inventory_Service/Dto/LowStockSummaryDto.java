package com.smartinventorymanagement.Inventory_Service.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LowStockSummaryDto {
    private Long productId;
    private String productName;
    private int totalQuantity;
    private int thresholdQuantity;
}

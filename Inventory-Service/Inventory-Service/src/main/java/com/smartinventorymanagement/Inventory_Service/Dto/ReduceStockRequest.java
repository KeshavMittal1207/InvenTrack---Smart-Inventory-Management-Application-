package com.smartinventorymanagement.Inventory_Service.Dto;

import lombok.Data;

@Data
public class ReduceStockRequest {
    private Long productId;
    private int quantity;

}

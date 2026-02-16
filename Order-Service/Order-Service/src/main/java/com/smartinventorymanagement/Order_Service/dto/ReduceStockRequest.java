package com.smartinventorymanagement.Order_Service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReduceStockRequest {
    private Long productId;
    private int quantity;

}

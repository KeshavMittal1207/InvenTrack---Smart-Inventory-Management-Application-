package com.smartinventorymanagement.Order_Service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaceOrderRequest {
    private Long productId;
    private Integer quantity;
}

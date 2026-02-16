package com.smartinventorymanagement.Inventory_Service.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StockByProductDto {
    private String Product;
    private int stock;
}

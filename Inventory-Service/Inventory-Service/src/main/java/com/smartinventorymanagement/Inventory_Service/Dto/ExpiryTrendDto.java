package com.smartinventorymanagement.Inventory_Service.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExpiryTrendDto {
    private String day;
    private long batches;
}

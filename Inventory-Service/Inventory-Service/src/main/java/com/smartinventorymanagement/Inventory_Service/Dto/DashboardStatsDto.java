package com.smartinventorymanagement.Inventory_Service.Dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DashboardStatsDto {

    private long totalProducts;
    private long totalStock;
    private long lowStockProducts;
    private long expiringSoon; 
    private List<AlertDto> alerts;
}

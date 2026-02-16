package com.smartinventorymanagement.Inventory_Service.Dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class DashboardOverviewDto {
    private long totalProducts;
    private long totalStock;
    private long lowStockProducts;
    private long expiringSoon;

    private List<AlertDto> recentAlerts;
}

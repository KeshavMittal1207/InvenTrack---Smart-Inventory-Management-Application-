package com.smartinventorymanagement.Inventory_Service.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartinventorymanagement.Inventory_Service.Dto.DashboardStatsDto;
import com.smartinventorymanagement.Inventory_Service.Dto.ExpiryTrendDto;
import com.smartinventorymanagement.Inventory_Service.Dto.LowStockSummaryDto;
import com.smartinventorymanagement.Inventory_Service.Dto.RecentMovementDto;
import com.smartinventorymanagement.Inventory_Service.Dto.StockByProductDto;
import com.smartinventorymanagement.Inventory_Service.Service.DashboardService;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/overview")
    public ResponseEntity<DashboardStatsDto> getStats() {
        return ResponseEntity.ok(dashboardService.getOverview());
    }

    @GetMapping("/stock-by-product")
    public ResponseEntity<List<StockByProductDto>> stockByProduct() {
        return ResponseEntity.ok(dashboardService.stockByProduct());
    }

    @GetMapping("/expiry-trend")
    public ResponseEntity<List<ExpiryTrendDto>> expiryTrend() {
        return ResponseEntity.ok(dashboardService.expiryTrend());
    }

    @GetMapping("/low-stock-summary")
    public ResponseEntity<List<LowStockSummaryDto>> lowStockSummary() {
        return ResponseEntity.ok(dashboardService.lowStockSummary());
    }

    @GetMapping("/recent-movements")
    public ResponseEntity<List<RecentMovementDto>> recentMovements() {
        return ResponseEntity.ok(dashboardService.recentMovements());
    }
}

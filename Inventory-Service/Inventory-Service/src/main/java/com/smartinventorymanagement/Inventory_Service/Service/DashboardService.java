package com.smartinventorymanagement.Inventory_Service.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartinventorymanagement.Inventory_Service.Dto.AlertDto;
import com.smartinventorymanagement.Inventory_Service.Dto.DashboardStatsDto;
import com.smartinventorymanagement.Inventory_Service.Dto.ExpiryTrendDto;
import com.smartinventorymanagement.Inventory_Service.Dto.StockByProductDto;
import com.smartinventorymanagement.Inventory_Service.Repository.InventoryBatchRepository;
import com.smartinventorymanagement.Inventory_Service.Repository.ProductRepository;
import com.smartinventorymanagement.Inventory_Service.Repository.StockSummaryRepository;
import com.smartinventorymanagement.Inventory_Service.config.AlertFeignClient;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DashboardService {
    
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StockSummaryRepository stockSummaryRepository;

    @Autowired
    private InventoryBatchRepository inventoryBatchRepository;

    @Autowired
    private AlertFeignClient alertFeignClient;

    public DashboardStatsDto getOverview(){

        long totalProducts = productRepository.count();
        long totalStock = Optional.ofNullable(stockSummaryRepository.getTotalStock()).orElse(0L);        
        long lowStock = stockSummaryRepository.countLowStockProducts();        

        Date now = new Date();
        Date cutoff = Date.from(
                LocalDate.now().plusDays(30)
                        .atStartOfDay(ZoneId.systemDefault())
                        .toInstant()
        );

        long expiringSoon =
                inventoryBatchRepository.countExpiringSoon(now, cutoff);

        List<AlertDto> alerts = alertFeignClient.getRecentAlerts();

        return new DashboardStatsDto(
                totalProducts,
                totalStock,
                lowStock,
                expiringSoon,
                alerts
        );
    }
    public List<StockByProductDto> stockByProduct() {
        return stockSummaryRepository.stockByProduct();
    }

    public List<ExpiryTrendDto> expiryTrend() {
        Date now = new Date();
        Date cutoff = Date.from(
                LocalDate.now().plusDays(30)
                        .atStartOfDay(ZoneId.systemDefault())
                        .toInstant()
        );

        List<Object[]> rows =
                inventoryBatchRepository.expiryTrend(now, cutoff);

        List<ExpiryTrendDto> result = new ArrayList<>();
        for (Object[] row : rows) {
            result.add(new ExpiryTrendDto(
                    row[0].toString(),
                    (Long) row[1]
            ));
        }
        return result;
    }

}

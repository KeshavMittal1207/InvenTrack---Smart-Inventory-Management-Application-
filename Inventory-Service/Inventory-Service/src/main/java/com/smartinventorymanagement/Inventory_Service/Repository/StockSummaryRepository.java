package com.smartinventorymanagement.Inventory_Service.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.smartinventorymanagement.Inventory_Service.Model.StockSummary;

public interface StockSummaryRepository extends JpaRepository<StockSummary,Long> {
    @Query("SELECT SUM(s.totalQuantity) FROM StockSummary s")
    Long getTotalStock();

    @Query("""
    SELECT COUNT(s)
    FROM StockSummary s
    WHERE s.totalQuantity <= (
        SELECT COALESCE(SUM(b.thresholdQuantity), 0)
        FROM InventoryBatch b
        WHERE b.productId = s.productId
    )
""")
long countLowStockProducts();

    @Query("""
        SELECT new com.smartinventorymanagement.Inventory_Service.Dto.StockByProductDto(
            p.name,
            s.totalQuantity
        )
        FROM Product p
        JOIN StockSummary s ON p.productId = s.productId
    """)
    List<com.smartinventorymanagement.Inventory_Service.Dto.StockByProductDto> stockByProduct();

    @Query("""
        SELECT s
        FROM StockSummary s
        WHERE s.totalQuantity <= (
            SELECT COALESCE(SUM(b.thresholdQuantity), 0)
            FROM InventoryBatch b
            WHERE b.productId = s.productId
        )
    """)
    List<StockSummary> findLowStockProducts();
}

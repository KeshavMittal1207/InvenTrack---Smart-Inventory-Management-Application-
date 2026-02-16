package com.smartinventorymanagement.Inventory_Service.Repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smartinventorymanagement.Inventory_Service.Enums.BatchStatus;
import com.smartinventorymanagement.Inventory_Service.Model.InventoryBatch;

public interface InventoryBatchRepository
        extends JpaRepository<InventoryBatch, Long> {

    List<InventoryBatch>
        findByProductIdAndStatusOrderByExpiryDateAsc(
            Long productId,
            BatchStatus status
        );

    // 2️⃣ Expired batches
    @Query("""
        SELECT b FROM InventoryBatch b
        WHERE b.expiryDate < :now
          AND b.status = :status
    """)
    List<InventoryBatch> findExpiredBatches(
            @Param("now") Date now,
            @Param("status") BatchStatus status
    );

    // 3️⃣ Near-expiry batches
    @Query("""
        SELECT b FROM InventoryBatch b
        WHERE b.expiryDate < :cutoff
          AND b.status = :status
    """)
    List<InventoryBatch> findNearExpiryBatches(
            @Param("cutoff") Date cutoff,
            @Param("status") BatchStatus status
    );
    @Query("""
    SELECT COUNT(b)
    FROM InventoryBatch b
    WHERE b.expiryDate BETWEEN :now AND :cutoff
      AND b.status = 'ACTIVE'
""")
long countExpiringSoon(Date now, Date cutoff);

@Query("""
    SELECT FUNCTION('DATE', b.expiryDate), COUNT(b)
    FROM InventoryBatch b
    WHERE b.expiryDate BETWEEN :now AND :cutoff
    GROUP BY FUNCTION('DATE', b.expiryDate)
    ORDER BY FUNCTION('DATE', b.expiryDate)
""")
List<Object[]> expiryTrend(Date now, Date cutoff);
}

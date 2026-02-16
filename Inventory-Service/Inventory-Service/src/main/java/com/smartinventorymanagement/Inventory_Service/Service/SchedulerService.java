package com.smartinventorymanagement.Inventory_Service.Service;

import com.smartinventorymanagement.Inventory_Service.Dto.AlertDto;
import com.smartinventorymanagement.Inventory_Service.Enums.BatchStatus;
import com.smartinventorymanagement.Inventory_Service.Enums.MovementType;
import com.smartinventorymanagement.Inventory_Service.Model.InventoryBatch;
import com.smartinventorymanagement.Inventory_Service.Model.StockMovement;
import com.smartinventorymanagement.Inventory_Service.Model.StockSummary;
import com.smartinventorymanagement.Inventory_Service.Repository.InventoryBatchRepository;
import com.smartinventorymanagement.Inventory_Service.Repository.StockMovementRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class SchedulerService {

    @Autowired
    private KafkaTemplate<String, AlertDto> kafkaTemplate;

    @Autowired
    private InventoryBatchRepository inventoryBatchRepository;

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private StockMovementRepository stockMovementRepository;

    @Scheduled(cron = "0 * * * * *")
    @Transactional
    public void runInventoryChecks() {
        LocalDate today = LocalDate.now();
        Date now = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());

        handleExpiredBatches(now);
        handleNearExpiryBatches(today);
        handleLowStock(today);
    }

    private void handleExpiredBatches(Date now) {

        List<InventoryBatch> expiredBatches =
                inventoryBatchRepository.findExpiredBatches(now , BatchStatus.ACTIVE);

        for (InventoryBatch batch : expiredBatches) {

            log.warn("Marking batch {} as EXPIRED", batch.getBatchId());

            int expiredQty = batch.getQuantity(); 

            batch.setStatus(BatchStatus.EXPIRED);
            batch.setQuantity(0);

            stockMovementRepository.save(
                    StockMovement.builder()
                            .productId(batch.getProductId())
                            .batchId(batch.getBatchId())
                            .movementType(MovementType.EXPIRED)
                            .quantity(expiredQty)
                            .createdAt(new Date())
                            .build()
            );
            inventoryService.updateSummary(batch.getProductId() , -expiredQty);
        }
    }

    private void handleNearExpiryBatches(LocalDate today) {

        Date cutoff = Date.from(
                today.plusDays(15)
                     .atStartOfDay(ZoneId.systemDefault())
                     .toInstant()
        );

        List<InventoryBatch> nearExpiry =
                inventoryBatchRepository.findNearExpiryBatches(cutoff , BatchStatus.ACTIVE);

        for (InventoryBatch batch : nearExpiry) {

            AlertDto alert = AlertDto.builder()
                .alertType("NEAR_EXPIRY")
                .batchId(batch.getBatchId())
                .productId(batch.getProductId())
                .createdAt(today)
                .build();

            kafkaTemplate.send("near-expiry", alert);
        }
    }

    private void handleLowStock(LocalDate today) {

        List<StockSummary> lowStockProducts =
                inventoryService.getLowStockProducts();

        for (StockSummary summary : lowStockProducts) {

            AlertDto alert = AlertDto.builder()
                .alertType("LOW_STOCK")
                .batchId(null)
                .productId(summary.getProductId())
                .createdAt(today)
                .build();

            kafkaTemplate.send("low-stock", alert);
        }
    }
}

package com.smartinventorymanagement.Inventory_Service.Service;

import com.smartinventorymanagement.Inventory_Service.Repository.InventoryBatchRepository;
import com.smartinventorymanagement.Inventory_Service.Repository.StockMovementRepository;
import com.smartinventorymanagement.Inventory_Service.Repository.StockSummaryRepository;
import com.smartinventorymanagement.Inventory_Service.config.SellerFeignClient;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.smartinventorymanagement.Inventory_Service.Dto.AddBatchRequest;
import com.smartinventorymanagement.Inventory_Service.Enums.BatchStatus;
import com.smartinventorymanagement.Inventory_Service.Enums.MovementType;
import com.smartinventorymanagement.Inventory_Service.Enums.ProductStatus;
import com.smartinventorymanagement.Inventory_Service.Model.InventoryBatch;
import com.smartinventorymanagement.Inventory_Service.Model.Product;
import com.smartinventorymanagement.Inventory_Service.Model.StockMovement;
import com.smartinventorymanagement.Inventory_Service.Model.StockSummary;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class InventoryService {
    @Autowired
    private InventoryBatchRepository batchRepo;
    @Autowired
    private StockSummaryRepository summaryRepo;
    @Autowired
    private StockMovementRepository movementRepo;
    @Autowired
    private SellerFeignClient sellerFeignClient;
    @Autowired
    private ProductService productService;

    public List<InventoryBatch> getBatchesByProduct(Long productId){
        return batchRepo.findByProductIdAndStatusOrderByExpiryDateAsc(productId, BatchStatus.ACTIVE);
    }
    @Transactional
    public void addBatch(AddBatchRequest req) {
        Product product = productService.getProduct(req.getProductId());
        if(product.getStatus() != ProductStatus.ACTIVE){
            throw new RuntimeException("Product inactive");
        }
        Boolean isSellerValid = sellerFeignClient.validateSeller(req.getSellerId());
        if(Boolean.FALSE.equals(isSellerValid)){
            throw new RuntimeException(
                "Seller is invalid or inactive : " + req.getSellerId() 
            );
        }

        InventoryBatch batch = InventoryBatch.builder()
            .productId(req.getProductId())
            .batchNo(req.getBatchNo())
            .quantity(req.getQuantity())
            .thresholdQuantity(req.getThresholdQuantity())
            .expiryDate(req.getExpiryDate())
            .sellerId(req.getSellerId())
            .status(BatchStatus.ACTIVE)
            .build();
        batchRepo.save(batch);

        updateSummary(req.getProductId() , req.getQuantity());

        movementRepo.save(StockMovement.builder()
            .productId(req.getProductId())
            .batchId(batch.getBatchId())
            .movementType(MovementType.INWARD)
            .quantity(req.getQuantity())
            .build());
        
    }


    public List<InventoryBatch> getAllBatches(){
        return batchRepo.findAll();
    }

    public List<InventoryBatch> getExpiringWithin(int days) {

        Date cutoff = new Date(
                System.currentTimeMillis() + (long) days * 24 * 60 * 60 * 1000
        );
    
        return batchRepo.findNearExpiryBatches(
                cutoff,
                BatchStatus.ACTIVE
        );
    }

    public StockSummary getStockSummary(Long productId) {
        return summaryRepo.findById(productId)
                .orElse(new StockSummary(productId , 0, null));
    }
    
    

//     @Cacheable(value = "Inventory_Cache" , key = "#sellerId")
//     public List<InventoryEntry> getInventoryBySellerId(String sellerId) {
//         return inventoryEntryRepository.findBySellerId(sellerId).stream().toList();
//     }

//     @Cacheable(value = "Inventory_Cache" , key = "#inventoryId")
//     public InventoryEntry getInventoryByInventoryId(Long inventoryId) { 
//         return inventoryEntryRepository.findByInventoryId(inventoryId)
//             .orElseThrow(() -> new RuntimeException("Inventory Not found : " + inventoryId));
// }


//     @Cacheable(value = "Inventory_Cache" , key = "'allInventories'")
//     public List<InventoryEntry> getInventory() {
//         log.info("through database");
//         return inventoryEntryRepository.findAll();
//     }

//     @Cacheable(value = "Item_Cache" , key = "'allItems'")
//     public List<Item> getItems() {
//         log.warn("Item DB");
//         return itemRepository.findAll();
//     }

    @Transactional
    public void reduceStock(Long productId , int quantity){
        List<InventoryBatch> batches = batchRepo.findByProductIdAndStatusOrderByExpiryDateAsc(productId , BatchStatus.ACTIVE);
        int remaining = quantity;

        for(InventoryBatch batch :  batches){
            if(remaining <= 0){ break; }
            int deduct = Math.min(batch.getQuantity(), remaining);
            batch.setQuantity(batch.getQuantity() - deduct);

            if(batch.getQuantity() == 0){
                batch.setStatus(BatchStatus.SOLD_OUT);
            }

            remaining -= deduct;

            movementRepo.save(StockMovement.builder()
                        .productId(productId)
                        .batchId(batch.getBatchId())
                        .movementType(MovementType.SALE)
                        .quantity(deduct)
                        .build());
        }

        if(remaining > 0 ){
            throw new RuntimeException("Insufficient Stock");
        }
        updateSummary(productId , -quantity);
    }

    public void updateSummary(Long productId, int delta) {
        StockSummary summary =
                summaryRepo.findById(productId)
                        .orElseGet(() -> {
                            StockSummary s = new StockSummary();
                            s.setProductId(productId);
                            s.setTotalQuantity(0);
                            s.setLastUpdated(new Date());
                            return s;
                        });

        summary.setTotalQuantity(summary.getTotalQuantity() + delta);
        summary.setLastUpdated(new Date());

        summaryRepo.save(summary);
    }

    public List<StockSummary> getLowStockProducts(){
        return summaryRepo.findLowStockProducts();
    }

    // @Cacheable(value = "Item_Cache" , key = "#itemId")
    // public Item getItemByItemId(String itemId) {
    //     log.info("Item DB 2");
    //     return itemRepository.findByItemId(itemId);
    // }
    // @Cacheable(value = "Inventory_Cache" ,key = "#itemId")
    // public InventoryEntry getInventoryByItemId(String itemId) {
    //     return inventoryEntryRepository.findByItemId(itemId);
    // }

    // @Cacheable(value = "Item_Cache" , key = "#inventoryId")
    // public List<Item> getItemByInventoryId(Long inventoryId) {
    //     Optional<InventoryEntry> inventoryEntry = inventoryEntryRepository.findByInventoryId(inventoryId);
    //     if (inventoryEntry.isPresent()){
    //         return inventoryEntry.get().getItems();
    //     }else {
    //         throw new RuntimeException("Inventory Entry not found with ID: " + inventoryId);
    //     }
    // }
}

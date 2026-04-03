package com.smartinventorymanagement.Inventory_Service.Controller;

import com.smartinventorymanagement.Inventory_Service.Service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.smartinventorymanagement.Inventory_Service.Dto.AddBatchRequest;
import com.smartinventorymanagement.Inventory_Service.Dto.ReduceStockRequest;
import com.smartinventorymanagement.Inventory_Service.Model.InventoryBatch;
import com.smartinventorymanagement.Inventory_Service.Model.StockSummary;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryEntryService;

    @PreAuthorize("hasRole('SHOP_OWNER')")
    @PostMapping("/add-batch")
    public ResponseEntity<String> addBatch(@RequestBody AddBatchRequest request){
        inventoryEntryService.addBatch(request);
        return ResponseEntity.ok("Batch Added Successfully");
    }
    
    @PreAuthorize("hasRole('STAFF') or hasRole('SHOP_OWNER')")
    @GetMapping("/batches-by-product")
    public ResponseEntity<List<InventoryBatch>> getBatches(@RequestParam Long productId){
        return ResponseEntity.ok(inventoryEntryService.getBatchesByProduct(productId));
    }

    @PreAuthorize("hasRole('STAFF') or hasRole('SHOP_OWNER')")
    @GetMapping("/AllBatches")
    public ResponseEntity<List<InventoryBatch>> getAllBatches(){
        return ResponseEntity.ok(inventoryEntryService.getAllBatches());
    }

    @PreAuthorize("hasRole('STAFF') or hasRole('SHOP_OWNER')")
    @PutMapping("/reduce-stock")
    public ResponseEntity<String> reduceStock(@RequestBody ReduceStockRequest request){
        inventoryEntryService.reduceStock(request.getProductId() , request.getQuantity());
        return ResponseEntity.ok("Stock reduced successfully");
    }
    @PreAuthorize("hasRole('STAFF') or hasRole('SHOP_OWNER')")
    @GetMapping("/expiring")
    public ResponseEntity<List<InventoryBatch>> getExpiringSoon(
            @RequestParam int days) {

        return ResponseEntity.ok(
                inventoryEntryService.getExpiringWithin(days)
        );
    }
    @PreAuthorize("hasRole('STAFF') or hasRole('SHOP_OWNER')")
    @GetMapping("/summary")
    public ResponseEntity<StockSummary> getStockSummary(
            @RequestParam Long productId) {

        return ResponseEntity.ok(
                inventoryEntryService.getStockSummary(productId)
        );
    }
}

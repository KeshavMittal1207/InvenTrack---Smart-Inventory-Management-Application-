package com.smartinventorymanagement.Inventory_Service.Controller;

import com.smartinventorymanagement.Inventory_Service.Service.InventoryEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.smartinventorymanagement.Inventory_Service.Model.InventoryEntry;
import com.smartinventorymanagement.Inventory_Service.Model.Item;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryEntryController {

    @Autowired
    private InventoryEntryService inventoryEntryService;

    @PostMapping("/addInventory")
    public ResponseEntity<InventoryEntry> addInventory(@RequestBody InventoryEntry inventoryEntry){
        inventoryEntryService.addInventory(inventoryEntry);
        return ResponseEntity.ok(inventoryEntry);
    }
    @GetMapping("/getInventoryBySellerId")
    public ResponseEntity<List<InventoryEntry>> getInventoryBySellerId(@RequestParam String sellerId){
        List<InventoryEntry> inventoryEntry = inventoryEntryService.getInventoryBySellerId(sellerId);
        return ResponseEntity.ok(inventoryEntry);
    }
    @GetMapping("/getInventoryByInventoryId")
    public ResponseEntity<InventoryEntry> getInventoryByInventoryId(@RequestParam Long inventoryId){
        InventoryEntry inventoryEntry = inventoryEntryService.getInventoryByInventoryId(inventoryId);
        return ResponseEntity.ok(inventoryEntry);
    }
    @GetMapping("/getInventoryByItemId")
    public ResponseEntity<InventoryEntry> getInventoryByItemId(@RequestParam String itemId){
        InventoryEntry inventoryEntry = inventoryEntryService.getInventoryByItemId(itemId);
        return ResponseEntity.ok(inventoryEntry);
    }
    @GetMapping("/getInventory")
    public List<InventoryEntry> getInventory(){
        return inventoryEntryService.getInventory();
    }
    @GetMapping("/getItemByItemId")
    public Item getItemByItemId(@RequestParam String itemId){
        return inventoryEntryService.getItemByItemId(itemId);
    }
    @GetMapping("/getItemByInventoryId")
    public List<Item> getItemByInventoryId(@RequestParam Long inventoryId){
        return inventoryEntryService.getItemByInventoryId(inventoryId);
    }
    @GetMapping("/getItems")
    public List<Item> getItems(){
        return inventoryEntryService.getItems();
    }

    @PutMapping("/reduceItem")
    public void reduceItem(@RequestParam String itemId ,@RequestParam int quantity){
        inventoryEntryService.reduceItem(itemId,quantity);
    }
}

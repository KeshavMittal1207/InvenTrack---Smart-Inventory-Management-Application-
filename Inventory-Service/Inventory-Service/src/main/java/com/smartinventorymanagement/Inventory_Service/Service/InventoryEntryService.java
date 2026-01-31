package com.smartinventorymanagement.Inventory_Service.Service;

import com.smartinventorymanagement.Inventory_Service.Repository.InventoryEntryRepository;
import com.smartinventorymanagement.Inventory_Service.Repository.ItemRepository;

import lombok.extern.slf4j.Slf4j;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.smartinventorymanagement.Inventory_Service.Model.InventoryEntry;
import com.smartinventorymanagement.Inventory_Service.Model.Item;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class InventoryEntryService {
    @Autowired
    private InventoryEntryRepository inventoryEntryRepository;

    @Autowired
    private ItemRepository itemRepository;

    @CachePut(value = "Inventory_Cache", key = "#result.inventoryId")
    public InventoryEntry addInventory(InventoryEntry inventoryEntry) {
        log.warn("Inventory DB");
        InventoryEntry inventoryEntry2 = inventoryEntryRepository.save(inventoryEntry);
        for (Item item : inventoryEntry.getItems()) {
            item.setInventoryId(inventoryEntry2.getInventoryId());
            itemRepository.save(item);
        }
        return inventoryEntry2;
    }

    @Cacheable(value = "Inventory_Cache" , key = "#sellerId")
    public List<InventoryEntry> getInventoryBySellerId(String sellerId) {
        return inventoryEntryRepository.findBySellerId(sellerId).stream().toList();
    }

    @Cacheable(value = "Inventory_Cache" , key = "#inventoryId")
    public InventoryEntry getInventoryByInventoryId(Long inventoryId) { 
        return inventoryEntryRepository.findByInventoryId(inventoryId)
            .orElseThrow(() -> new RuntimeException("Inventory Not found : " + inventoryId));
}


    @Cacheable(value = "Inventory_Cache" , key = "'allInventories'")
    public List<InventoryEntry> getInventory() {
        log.info("through database");
        return inventoryEntryRepository.findAll();
    }

    @Cacheable(value = "Item_Cache" , key = "'allItems'")
    public List<Item> getItems() {
        log.warn("Item DB");
        return itemRepository.findAll();
    }

    @CachePut(value = "Item_Cache" , key = "#result.itemId")
    @CacheEvict(value = "Item_Cache" , key = "'allItems'")
    public Item reduceItem(String itemId , int quantity){
        Item i = itemRepository.findByItemId(itemId);
        i.setQuantity(i.getQuantity()-quantity);
        return itemRepository.save(i);
    }

    @Cacheable(value = "Item_Cache" , key = "#itemId")
    public Item getItemByItemId(String itemId) {
        log.info("Item DB 2");
        return itemRepository.findByItemId(itemId);
    }
    @Cacheable(value = "Inventory_Cache" ,key = "#itemId")
    public InventoryEntry getInventoryByItemId(String itemId) {
        return inventoryEntryRepository.findByItemId(itemId);
    }

    @Cacheable(value = "Item_Cache" , key = "#inventoryId")
    public List<Item> getItemByInventoryId(Long inventoryId) {
        Optional<InventoryEntry> inventoryEntry = inventoryEntryRepository.findByInventoryId(inventoryId);
        if (inventoryEntry.isPresent()){
            return inventoryEntry.get().getItems();
        }else {
            throw new RuntimeException("Inventory Entry not found with ID: " + inventoryId);
        }
    }
}

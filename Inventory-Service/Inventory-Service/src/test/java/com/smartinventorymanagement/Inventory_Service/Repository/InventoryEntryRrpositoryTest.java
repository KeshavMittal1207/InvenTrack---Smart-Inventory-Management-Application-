package com.smartinventorymanagement.Inventory_Service.Repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.smartinventorymanagement.Inventory_Service.Model.InventoryEntry;
import com.smartinventorymanagement.Inventory_Service.Model.Item;

@DataJpaTest
public class InventoryEntryRrpositoryTest {

    @Autowired
    private InventoryEntryRepository inventoryEntryRepository;

    private Item item;
    private InventoryEntry inventory;

    @BeforeEach
    void init(){

        item = Item.builder().expiryDate(new Date())
        .category("Drinks")
        .name("Sting")
        .quantity(15)
        .thresholdQuantity(20)
        .build();

        inventory = InventoryEntry.builder()
        .addedOn(new Date())
        .items(List.of(item))
        .sellerId("abcd")
        .build();
        
    }
    @Test
    void shouldReturnInventoryEntryIfFoundBySellerId(){
        String sellerId = "abcd";

        InventoryEntry saved = inventoryEntryRepository.save(inventory);

        List<InventoryEntry> found = inventoryEntryRepository.findBySellerId(sellerId);

        assertNotNull(found);
        assertNotNull(saved);
        assertEquals(List.of(saved), found);
        assertEquals(saved.getInventoryId(), found.getFirst().getInventoryId());
    }

    @Test
    void shouldNotReturnInventoryEntryIfNotFoundBySellerId(){
        
        InventoryEntry saved = inventoryEntryRepository.save(inventory);

        List<InventoryEntry> found = inventoryEntryRepository.findBySellerId("pqrs");

        assertTrue(found.isEmpty());
        assertNotEquals(List.of(saved), found);
    }

    @Test
    void shouldReturnInventoryEntryIfFoundByInventoryId(){
        
        InventoryEntry saved = inventoryEntryRepository.save(inventory);

        Optional< InventoryEntry> inventoryEntry = inventoryEntryRepository.findByInventoryId(saved.getInventoryId());

        assertNotNull(inventoryEntry.get());
        assertEquals("abcd", inventoryEntry.get().getSellerId());
    }

    @Test
    void shouldNotReturnInventoryEntryIfNotFoundByInventoryId(){

        InventoryEntry saved = inventoryEntryRepository.save(inventory);

        inventoryEntryRepository.findByInventoryId(4321l);

        assertNotEquals(4321l, saved.getInventoryId());
    }


}

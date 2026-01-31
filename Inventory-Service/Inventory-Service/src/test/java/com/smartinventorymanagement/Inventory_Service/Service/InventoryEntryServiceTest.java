package com.smartinventorymanagement.Inventory_Service.Service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.smartinventorymanagement.Inventory_Service.Model.InventoryEntry;
import com.smartinventorymanagement.Inventory_Service.Model.Item;
import com.smartinventorymanagement.Inventory_Service.Repository.InventoryEntryRepository;
import com.smartinventorymanagement.Inventory_Service.Repository.ItemRepository;

@ExtendWith(MockitoExtension.class)
public class InventoryEntryServiceTest {

    @Mock
    private InventoryEntryRepository inventoryEntryRepository;

    @Mock
    private ItemRepository itemRepository;

    private InventoryEntry inventoryEntryRequest;
    private InventoryEntry inventoryEntry;
    private Item itemRequest;
    private Item item;

    @InjectMocks
    private InventoryEntryService inventoryEntryService;

    @BeforeEach
    void init(){
        itemRequest = Item.builder()
        .category("Drinks")
        .expiryDate(new Date())
        .inventoryId(1234l)
        .name("Sting")
        .quantity(12)
        .thresholdQuantity(15)
        .build();

        inventoryEntryRequest = InventoryEntry.builder()
        .addedOn(new Date())
        .items(List.of(itemRequest))
        .sellerId("keshav")
        .build();

        item = Item.builder()
        .category("Drinks")
        .expiryDate(new Date())
        .inventoryId(1234l)
        .itemId("abcd")
        .name("Sting")
        .quantity(12)
        .thresholdQuantity(15)
        .build();

        inventoryEntry = InventoryEntry.builder()
        .inventoryId(1234l)
        .addedOn(new Date())
        .items(List.of(item))
        .sellerId("keshav")
        .build();

       
    }

    @Test
    void addInventoryTest(){

        inventoryEntryService.addInventory(inventoryEntryRequest);

        verify(inventoryEntryRepository , times(1)).save(inventoryEntryRequest);
        verify(itemRepository , times(1)).save(itemRequest);
    }

    @Test
    void getInventoryBySellerIdTest(){
        String sellerId = "keshav";

        when(inventoryEntryRepository.findBySellerId(sellerId)).thenReturn(List.of(inventoryEntry));

        List<InventoryEntry> inventories = inventoryEntryService.getInventoryBySellerId(sellerId);

        verify(inventoryEntryRepository , times(1)).findBySellerId(sellerId);

        assertNotNull(inventories);
        assertEquals(inventories.size(), 1);
        assertEquals(inventories.get(0).getInventoryId(), inventoryEntry.getInventoryId());
    }

    @Test
    void getInventoryByInventoryIdTest(){
        Long inventoryId = 1234l;

        when(inventoryEntryRepository.findByInventoryId(inventoryId)).thenReturn(Optional.of(inventoryEntry));

        InventoryEntry inventories =  inventoryEntryService.getInventoryByInventoryId(inventoryId);

        verify(inventoryEntryRepository , times(1)).findByInventoryId(inventoryId);
        assertNotNull(inventories);
        assertEquals(inventoryId, inventories.getInventoryId());

    }

    @Test
    void getInventoryTest(){
        
        when(inventoryEntryRepository.findAll()).thenReturn(List.of(inventoryEntry));

        List<InventoryEntry> inventories =  inventoryEntryService.getInventory();

        verify(inventoryEntryRepository , times(1)).findAll();
        assertNotNull(inventories);
        assertEquals(inventories.get(0), inventoryEntry);
        assertEquals(inventories.size(), 1);
    }

    @Test
    void getItemstest(){

        when(itemRepository.findAll()).thenReturn(List.of(item));
        List<Item> items = inventoryEntryService.getItems();

        verify(itemRepository , times(1)).findAll();
        assertNotNull(items);
        assertEquals(items.size(), 1);
        assertEquals(items.get(0), item);
    }

    @Test
    void reduceItemTest(){
        String itemId = "abcd";
        int quantity = 5 ;

        when(itemRepository.findByItemId(itemId)).thenReturn(item);
        inventoryEntryService.reduceItem(itemId, quantity);

        verify(itemRepository , times(1)).findByItemId(itemId);
        verify(itemRepository,times(1)).save(item);

        assertEquals(item.getQuantity(), 7);
    }

    @Test
    void getItemByItemIdTest(){
        String itemId = "abcd";

        when(itemRepository.findByItemId(itemId)).thenReturn(item);

        Item i = inventoryEntryService.getItemByItemId(itemId);

        assertNotNull(i);
        verify(itemRepository , times(1)).findByItemId(itemId);
        assertEquals(i, item);
        assertEquals(itemId, i.getItemId());
    }

    @Test
    void getInventoryByItemIdTest(){
        String itemId = "abcd";

        when(inventoryEntryRepository.findByItemId(itemId)).thenReturn(inventoryEntry);
        InventoryEntry i = inventoryEntryService.getInventoryByItemId(itemId);

        verify(inventoryEntryRepository , times(1)).findByItemId(itemId);
        assertNotNull(i);
        assertEquals(i, inventoryEntry);
        assertEquals(itemId, i.getItems().get(0).getItemId());
    }

    @Test
    void getItemByInventoryIdTest(){
        Long inventoryId = 1234l;
        when(inventoryEntryRepository.findByInventoryId(inventoryId)).thenReturn(Optional.of(inventoryEntry));
        List<Item> items = inventoryEntryService.getItemByInventoryId(inventoryId);

        assertNotNull(items);
        assertEquals(items.size(), 1);
        assertEquals(inventoryId, items.get(0).getInventoryId());
        assertDoesNotThrow(() -> new RuntimeException("InventoryEntry not found with ID: " + inventoryId));
    }

}

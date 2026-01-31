package com.smartinventorymanagement.Inventory_Service.Repository;

import com.smartinventorymanagement.Inventory_Service.Model.InventoryEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryEntryRepository extends JpaRepository<InventoryEntry ,Long> {

    List<InventoryEntry> findBySellerId(String sellerId);

    Optional<InventoryEntry> findByInventoryId(Long inventoryId);

    @Query("SELECT i FROM InventoryEntry i JOIN i.items item WHERE item.itemId = :itemId")
    InventoryEntry findByItemId(@Param("itemId") String itemId);
}

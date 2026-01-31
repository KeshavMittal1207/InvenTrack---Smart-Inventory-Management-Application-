package com.smartinventorymanagement.Inventory_Service.Repository;

import com.smartinventorymanagement.Inventory_Service.Model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item , String> {
    Item findByItemId(String itemId);
}

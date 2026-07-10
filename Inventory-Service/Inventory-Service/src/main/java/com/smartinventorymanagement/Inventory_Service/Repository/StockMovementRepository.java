package com.smartinventorymanagement.Inventory_Service.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartinventorymanagement.Inventory_Service.Model.StockMovement;

public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {
    List<StockMovement> findTop5ByOrderByCreatedAtDesc();
}

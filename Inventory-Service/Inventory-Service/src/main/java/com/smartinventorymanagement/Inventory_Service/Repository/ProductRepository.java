package com.smartinventorymanagement.Inventory_Service.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartinventorymanagement.Inventory_Service.Enums.ProductStatus;
import com.smartinventorymanagement.Inventory_Service.Model.Product;

public interface ProductRepository extends JpaRepository<Product , Long> {

    boolean existsByProductIdAndStatus(Long productId , ProductStatus status);

}

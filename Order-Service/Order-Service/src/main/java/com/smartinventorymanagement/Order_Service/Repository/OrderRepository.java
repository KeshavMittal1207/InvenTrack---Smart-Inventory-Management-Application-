package com.smartinventorymanagement.Order_Service.Repository;

import com.smartinventorymanagement.Order_Service.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order , String> {
}

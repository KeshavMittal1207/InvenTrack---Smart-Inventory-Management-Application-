package com.smartinventorymanagement.Order_Service.Service;

import com.smartinventorymanagement.Order_Service.Config.InventoryFeignClient;
import com.smartinventorymanagement.Order_Service.Model.Order;
import com.smartinventorymanagement.Order_Service.Repository.OrderRepository;
import com.smartinventorymanagement.Order_Service.dto.PlaceOrderRequest;
import com.smartinventorymanagement.Order_Service.dto.ReduceStockRequest;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private InventoryFeignClient inventoryFeignClient;

    @Transactional
    public Order placeOrder(PlaceOrderRequest request){

        inventoryFeignClient.reduceStock(new ReduceStockRequest(request.getProductId() , request.getQuantity()));


        Order order = Order.builder()
                .orderDate(LocalDate.now())
                .quantity(request.getQuantity())
                .productId(request.getProductId())
                .build();
        return orderRepository.save(order);
    }

    public List<Order> getOrders() {
        return orderRepository.findAll();
    }
}
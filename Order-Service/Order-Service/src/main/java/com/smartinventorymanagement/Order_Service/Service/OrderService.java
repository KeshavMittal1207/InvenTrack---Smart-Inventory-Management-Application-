package com.smartinventorymanagement.Order_Service.Service;

import com.smartinventorymanagement.Order_Service.Config.FeignClientConfig;
import com.smartinventorymanagement.Order_Service.Model.Order;
import com.smartinventorymanagement.Order_Service.Repository.OrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private FeignClientConfig feignClientConfig;

    @CachePut(value = "Orders_Cache" , key = "#result.orderId")
    @CacheEvict(value = "Orders_Cache" , key = "'allOrders'")
    public Order placeOrder(String itemId , int quantity){
        Order order = Order.builder()
                .orderDate(LocalDate.now())
                .quantity(quantity)
                .itemId(itemId)
                .inventoryId(feignClientConfig.getItemByItemId(itemId).getInventoryId())
                .build();
        return orderRepository.save(order);
    }

    @Cacheable(key = "'allOrders'" , value = "Orders_Cache")
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }
}
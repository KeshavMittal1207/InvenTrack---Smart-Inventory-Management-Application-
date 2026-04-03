package com.smartinventorymanagement.Order_Service.Controller;

import com.smartinventorymanagement.Order_Service.Model.Order;
import com.smartinventorymanagement.Order_Service.Service.OrderService;
import com.smartinventorymanagement.Order_Service.dto.PlaceOrderRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PreAuthorize("hasRole('STAFF') or hasRole('SHOP_OWNER')")
    @PostMapping("/placeOrder")
    public ResponseEntity<Order> placeOrder(@RequestBody PlaceOrderRequest request ){
        return ResponseEntity.ok(orderService.placeOrder(request));
    }
    
    @PreAuthorize("hasRole('STAFF') or hasRole('SHOP_OWNER')")
    @GetMapping("/getOrders")
    public List<Order> getOrders(){
        return orderService.getOrders();
    }
}

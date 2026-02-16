package com.smartinventorymanagement.Order_Service.Controller;

import com.smartinventorymanagement.Order_Service.Model.Order;
import com.smartinventorymanagement.Order_Service.Service.OrderService;
import com.smartinventorymanagement.Order_Service.dto.PlaceOrderRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/placeOrder")
    public ResponseEntity<Order> placeOrder(@RequestBody PlaceOrderRequest request ){
        return ResponseEntity.ok(orderService.placeOrder(request));
    }
    @GetMapping("/getOrders")
    public List<Order> getOrders(){
        return orderService.getOrders();
    }
}

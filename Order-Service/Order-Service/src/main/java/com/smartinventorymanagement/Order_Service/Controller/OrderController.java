package com.smartinventorymanagement.Order_Service.Controller;

import com.smartinventorymanagement.Order_Service.Model.Order;
import com.smartinventorymanagement.Order_Service.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/placeOrder/{itemId}/{quantity}")
    public ResponseEntity<Order> placeOrder(@PathVariable("itemId") String itemId , @PathVariable("quantity") int quantity){
        return ResponseEntity.ok(orderService.placeOrder(itemId , quantity));
    }
    @GetMapping("/getOrders")
    public List<Order> getOrders(){
        return orderService.getOrders();
    }
}

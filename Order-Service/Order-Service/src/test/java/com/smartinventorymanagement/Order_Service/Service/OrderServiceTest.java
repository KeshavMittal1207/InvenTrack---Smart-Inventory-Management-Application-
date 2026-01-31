package com.smartinventorymanagement.Order_Service.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.smartinventorymanagement.Order_Service.Config.FeignClientConfig;
import com.smartinventorymanagement.Order_Service.Model.Order;
import com.smartinventorymanagement.Order_Service.Repository.OrderRepository;
import com.smartinventorymanagement.Order_Service.dto.ItemDto;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    
    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private FeignClientConfig feignClientConfig;


    @Test
    void placeOrderTest(){
        String itemId = "ABCD1234";
        int quantity = 5;

        when(feignClientConfig.getItemByItemId(itemId)).thenReturn(
            ItemDto.builder()
            .itemId(itemId)
            .inventoryId(Long.valueOf(32))
            .build()
        );

        Order order = Order.builder()
        .itemId(itemId)
        .orderDate(LocalDate.now())
        .inventoryId(feignClientConfig.getItemByItemId(itemId).getInventoryId())
        .quantity(quantity)
        .build();

        Order savedOrder = Order.builder()
        .orderId("PQRS")
        .itemId(itemId)
        .orderDate(LocalDate.now())
        .inventoryId(feignClientConfig.getItemByItemId(itemId).getInventoryId())
        .quantity(quantity)
        .build();    

        when(orderRepository.save(order)).thenReturn(savedOrder);
        Order finalOrder = orderService.placeOrder(itemId , quantity);
        
        assertNotNull(finalOrder);
        assertEquals(savedOrder, finalOrder);
        assertEquals(savedOrder.getOrderId(), finalOrder.getOrderId());
    }

    @Test
    void getOrders(){
        Order order = Order.builder()
        .orderId("PQRS321")
        .itemId("ABCD123")
        .orderDate(LocalDate.now())
        .inventoryId((Long) 55l)
        .quantity(5)
        .build();


        when(orderRepository.findAll()).thenReturn(List.of(order));
        List<Order> orders = orderService.getOrders();

        assertNotNull(orders);
        assertEquals(1, orders.size());
        assertEquals(5, orders.get(0).getQuantity());
        assertEquals(List.of(order), orders);
    }
}

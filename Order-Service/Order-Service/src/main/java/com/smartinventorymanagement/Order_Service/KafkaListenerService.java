package com.smartinventorymanagement.Order_Service;

import com.smartinventorymanagement.Order_Service.Config.FeignClientConfig;
import com.smartinventorymanagement.Order_Service.Service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaListenerService {

    @Autowired
    private OrderService orderService;

    @Autowired
    private FeignClientConfig feignClientConfig;

    @KafkaListener(topics = "stock-order",groupId = "inventory-group")
    public void listen(String itemId){
        int quantity = feignClientConfig.getItemByItemId(itemId).getQuantity();
        int thresholdQuantity = feignClientConfig.getItemByItemId(itemId).getThresholdQuantity();
        orderService.placeOrder(itemId , (thresholdQuantity*2) - quantity);
    }
}

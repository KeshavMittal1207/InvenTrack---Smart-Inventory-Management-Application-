package com.smartinventorymanagement.Order_Service.Config;

import com.smartinventorymanagement.Order_Service.dto.ItemDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "Inventory-Service" , url = "http://localhost:8081")
public interface FeignClientConfig {

    @GetMapping("/inventory/getItemByItemId")
    ItemDto getItemByItemId(@RequestParam String itemId);

//    @PutMapping("/inventory/reduceItem")
//    void reduceItem(@RequestParam String itemId, @RequestParam int quantity);
}


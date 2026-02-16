package com.smartinventorymanagement.Order_Service.Config;

import com.smartinventorymanagement.Order_Service.dto.ReduceStockRequest;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "INVENTORY-SERVICE")
public interface InventoryFeignClient {

    @PutMapping("/inventory/reduce-stock")
    void reduceStock(@RequestBody ReduceStockRequest request);
}


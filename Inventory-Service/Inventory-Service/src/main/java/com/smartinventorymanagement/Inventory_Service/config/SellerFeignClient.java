package com.smartinventorymanagement.Inventory_Service.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "SELLER-SERVICE")
public interface SellerFeignClient {
    @GetMapping("/seller/validate/{sellerId}")
    Boolean validateSeller(@PathVariable String sellerId);
}

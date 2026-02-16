package com.smartinventorymanagement.Inventory_Service.config;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.smartinventorymanagement.Inventory_Service.Dto.AlertDto;

@FeignClient(name = "ALERT-SERVICE")
public interface AlertFeignClient {
    
    @GetMapping("/alert/recent")
    List<AlertDto> getRecentAlerts();
}

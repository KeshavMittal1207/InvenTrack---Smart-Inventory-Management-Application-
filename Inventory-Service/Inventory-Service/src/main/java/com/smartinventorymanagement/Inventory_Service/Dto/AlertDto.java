package com.smartinventorymanagement.Inventory_Service.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class AlertDto {

    private String alertId;
    private String alertType;
    private Long batchId;
    private Long productId;
    private LocalDate createdAt;
}


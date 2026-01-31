package com.smartinventorymanagement.Inventory_Service.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlertDto {
    private String alertType;
    private String itemId;
    private LocalDate date;
}


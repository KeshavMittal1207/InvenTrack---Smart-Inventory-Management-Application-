package com.smartinventorymanagement.Alert_Service.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlertDto {
    private String alertId;
    private String alertType;
    private String itemId;
    private LocalDate date;

}

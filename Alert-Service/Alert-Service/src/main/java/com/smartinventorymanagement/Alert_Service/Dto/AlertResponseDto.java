package com.smartinventorymanagement.Alert_Service.Dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlertResponseDto {

    private String alertId;
    private String alertType;
    private Long productId;
    private Long batchId;
    private LocalDate createdAt;
}

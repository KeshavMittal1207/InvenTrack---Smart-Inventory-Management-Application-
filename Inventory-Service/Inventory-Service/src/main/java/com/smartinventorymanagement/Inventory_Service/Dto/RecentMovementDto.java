package com.smartinventorymanagement.Inventory_Service.Dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RecentMovementDto {
    private Long movementId;
    private Long productId;
    private Long batchId;
    private String movementType;
    private int quantity;
    private Date createdAt;
}

package com.smartinventorymanagement.Inventory_Service.Dto;

import java.sql.Date;

import lombok.Data;

@Data
public class AddBatchRequest {
    
    private Long productId;
    private String batchNo;
    private int quantity;
    private int thresholdQuantity;
    private Date expiryDate;
    private String sellerId;
}

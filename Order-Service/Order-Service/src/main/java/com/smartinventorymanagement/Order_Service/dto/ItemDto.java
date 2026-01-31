package com.smartinventorymanagement.Order_Service.dto;


import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ItemDto {
    private String itemId;
    private String name;
    private String category;
    private int quantity;
    private int thresholdQuantity;
    private Long inventoryId;
    private Date expiryDate;
}
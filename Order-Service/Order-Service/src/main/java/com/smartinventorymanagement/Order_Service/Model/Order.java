package com.smartinventorymanagement.Order_Service.Model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Table(name = "`order`") // backticks to escape the keyword
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String orderId;

    @NotEmpty(message = "Item ID cannot be empty")
    private String itemId;

    @Min(value = 1 , message = "Quantity must be at least 1")
    private int quantity;

    private LocalDate orderDate;

    @NotNull(message = "Inventory Id cannot be null")
    @Positive(message = "Inventory Id must be positive")
    private Long inventoryId;

}

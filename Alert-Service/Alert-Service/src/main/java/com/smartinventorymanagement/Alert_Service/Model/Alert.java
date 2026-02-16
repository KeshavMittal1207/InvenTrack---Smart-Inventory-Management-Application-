package com.smartinventorymanagement.Alert_Service.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Alert {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String alertId;
    
    @Pattern(regexp = "LOW_STOCK|NEAR_EXPIRY" , message = "Alert type must be LOW_STOCK or NEAR_EXPIRY")
    @NotEmpty(message = "ALert type cannot be empty")
    private String alertType;

    private Long productId;
    private Long batchId;
    private LocalDate date;
    @PrePersist
    public void onCreate() {
        this.date = LocalDate.now();
    }
}

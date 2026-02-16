package com.smartinventorymanagement.Seller_Service.Model;

import java.util.Date;

import com.smartinventorymanagement.Seller_Service.Enums.SellerStatus;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String sellerId;

    @NotEmpty(message = "Name cannot be empty")
    private String name;
    
    @NotBlank(message = "Mobile number cannot be blank")
    @Pattern(regexp = "^[0-9]{10}$" , message = "Mobile number must be exactly 10 digits")
    private String mobile;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private SellerStatus status = SellerStatus.ACTIVE;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @PrePersist
    public void onCreate() {
        createdAt = new Date();
        updatedAt = new Date();
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = new Date();
    }

}
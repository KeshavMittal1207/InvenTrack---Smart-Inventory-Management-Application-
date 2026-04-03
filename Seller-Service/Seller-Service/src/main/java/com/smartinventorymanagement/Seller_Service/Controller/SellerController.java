package com.smartinventorymanagement.Seller_Service.Controller;

import com.smartinventorymanagement.Seller_Service.Model.Seller;
import com.smartinventorymanagement.Seller_Service.Service.SellerService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/seller")

public class SellerController {

    @Autowired
    private SellerService sellerService;

    @PreAuthorize("hasRole('SHOP_OWNER')")
    @PostMapping("/addSeller")
    public ResponseEntity<Seller> addSeller(@Valid @RequestBody Seller seller) {
        return ResponseEntity.ok(sellerService.addSeller(seller));
    }
    @GetMapping("/getAllSellers")
    @PreAuthorize("hasRole('STAFF') or hasRole('SHOP_OWNER')")    
    public ResponseEntity<List<Seller>> getAllSellers(){
        return ResponseEntity.ok(sellerService.getAllSeller());
    }
    @PreAuthorize("hasRole('STAFF') or hasRole('SHOP_OWNER')")
    @GetMapping("/getSeller/{sellerId}")
    public ResponseEntity<Seller> getSeller(@PathVariable String sellerId) {
        return ResponseEntity.ok(sellerService.getSeller(sellerId));
    }
    @PreAuthorize("hasRole('STAFF') or hasRole('SHOP_OWNER')")
    @GetMapping("/validate/{sellerId}")
    public ResponseEntity<Boolean> validateSeller(@PathVariable String sellerId) {
        return ResponseEntity.ok(
                sellerService.isSellerActive(sellerId)
        );
    }
    @PreAuthorize("hasRole('SHOP_OWNER')")
    @PatchMapping("/toggle-status/{sellerId}")
    public ResponseEntity<Seller> toggleStatus(@PathVariable String sellerId){
        return ResponseEntity.ok(sellerService.toggleStatus(sellerId));
    }
}

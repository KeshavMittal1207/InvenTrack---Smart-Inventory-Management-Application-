package com.smartinventorymanagement.Seller_Service.Controller;

import com.smartinventorymanagement.Seller_Service.Model.Seller;
import com.smartinventorymanagement.Seller_Service.Service.SellerService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/seller")

public class SellerController {

    @Autowired
    private SellerService sellerService;

    @PostMapping("/addSeller")
    public ResponseEntity<Seller> addSeller(@Valid @RequestBody Seller seller) {
        return ResponseEntity.ok(sellerService.addSeller(seller));
    }
    @GetMapping("/getAllSellers")
    public ResponseEntity<List<Seller>> getAllSellers(){
        return ResponseEntity.ok(sellerService.getAllSeller());
    }
    @GetMapping("/getSeller/{sellerId}")
    public ResponseEntity<Seller> getSeller(@PathVariable String sellerId) {
        return ResponseEntity.ok(sellerService.getSeller(sellerId));
    }
    @GetMapping("/validate/{sellerId}")
    public ResponseEntity<Boolean> validateSeller(
            @PathVariable String sellerId) {
        return ResponseEntity.ok(
                sellerService.isSellerActive(sellerId)
        );
    }

    @PatchMapping("/toggle-status/{sellerId}")
    public ResponseEntity<Seller> toggleStatus(@PathVariable String sellerId){
        return ResponseEntity.ok(sellerService.toggleStatus(sellerId));
    }
}

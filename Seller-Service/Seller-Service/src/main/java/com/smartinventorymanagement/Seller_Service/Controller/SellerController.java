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
        Seller seller1 = sellerService.addSeller(seller);
        return ResponseEntity.ok(seller1);
    }
    @GetMapping("/getSeller")
    public ResponseEntity<List<Seller>> getAllSeller(){
        return ResponseEntity.ok(sellerService.getAllSeller());
    }
    @GetMapping("/getSeller/{sellerId}")
    public ResponseEntity<Seller> getSellerById(@PathVariable String sellerId) {
        return sellerService.getSellerById(sellerId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

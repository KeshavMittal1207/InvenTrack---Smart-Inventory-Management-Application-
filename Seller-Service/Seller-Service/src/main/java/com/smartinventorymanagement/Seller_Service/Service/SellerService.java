package com.smartinventorymanagement.Seller_Service.Service;

import com.smartinventorymanagement.Seller_Service.Enums.SellerStatus;
import com.smartinventorymanagement.Seller_Service.Model.Seller;
import com.smartinventorymanagement.Seller_Service.Repository.SellerRepository;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Slf4j

public class SellerService {
    @Autowired
    private SellerRepository sellerRepository;

    public Seller addSeller(Seller seller) {
        return sellerRepository.save(seller);
    }

    public Seller getSeller(String sellerId) {
        return sellerRepository.findBySellerId(sellerId).orElseThrow(() -> new RuntimeException("Seller not found"));
    }

    public List<Seller> getAllSeller() {
        return sellerRepository.findAll();
    }

    public boolean isSellerActive(String sellerId) {
        return sellerRepository.existsBySellerIdAndStatus(
                sellerId,
                SellerStatus.ACTIVE
        );
    }

    public Seller toggleStatus(String sellerId){
        Seller seller = sellerRepository.findBySellerId(sellerId).orElseThrow(() -> new RuntimeException("Seller not found"));
        
        if(seller.getStatus() == SellerStatus.ACTIVE){
            seller.setStatus(SellerStatus.SUSPENDED);
        }
        else{
            seller.setStatus(SellerStatus.ACTIVE);
        }
        return sellerRepository.save(seller);
    }
}

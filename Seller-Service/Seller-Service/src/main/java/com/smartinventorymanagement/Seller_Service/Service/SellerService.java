package com.smartinventorymanagement.Seller_Service.Service;

import com.smartinventorymanagement.Seller_Service.Model.Seller;
import com.smartinventorymanagement.Seller_Service.Repository.SellerRepository;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j

public class SellerService {
    @Autowired
    private SellerRepository sellerRepository;

    @CachePut(value = "Seller_Cache" , key = "#result.sellerId")
    @CacheEvict(value = "Seller_Cache", key = "'allSellers'")
    public Seller addSeller(Seller seller) {
        return sellerRepository.save(seller);
    }

    @Cacheable(value = "Seller_Cache" , key = "#id")
    public Optional<Seller> getSellerById(String id) {
        return Optional.ofNullable(sellerRepository.findBySellerId(id));
    }

    @Cacheable(value="Seller_Cache", key = "'allSellers'")
    public List<Seller> getAllSeller() {
        return sellerRepository.findAll();
    }
}

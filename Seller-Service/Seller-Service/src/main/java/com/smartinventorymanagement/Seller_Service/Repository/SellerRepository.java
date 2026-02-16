package com.smartinventorymanagement.Seller_Service.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smartinventorymanagement.Seller_Service.Enums.SellerStatus;
import com.smartinventorymanagement.Seller_Service.Model.Seller;


@Repository
public interface SellerRepository extends JpaRepository<Seller , String> {
    Optional<Seller> findBySellerId(String id);

    boolean existsBySellerIdAndStatus(String sellerId , SellerStatus status);

}
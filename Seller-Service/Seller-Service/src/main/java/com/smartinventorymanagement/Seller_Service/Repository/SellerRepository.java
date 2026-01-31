package com.smartinventorymanagement.Seller_Service.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;import com.smartinventorymanagement.Seller_Service.Model.Seller;


@Repository
public interface SellerRepository extends JpaRepository<Seller , String> {
    Seller findBySellerId(String id);

}
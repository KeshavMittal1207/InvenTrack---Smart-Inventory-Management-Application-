package com.smartinventorymanagement.Seller_Service.Repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.smartinventorymanagement.Seller_Service.Model.Seller;

@DataJpaTest
public class SellerRepositoryTest {

    @Autowired
    private SellerRepository sellerRepository;

    private static Seller seller ;

    @BeforeAll
    static void init(){
        seller = Seller.builder()
        .mobile("9833881177")
        .name("Keshav")
        .build();
        }

    @Test
    void ShouldReturnSellerIfSellerFound(){

        Seller savedSeller  = sellerRepository.save(seller);
        Seller found = sellerRepository.findBySellerId(savedSeller.getSellerId());

        assertNotNull(found);
        assertNotNull(found.getSellerId());
        assertEquals(found.getMobile(), savedSeller.getMobile());
    }

    @Test
    void ShouldReturnNullIfSellerNotFound(){
        Seller savedSeller = sellerRepository.save(seller);
        Seller found = sellerRepository.findBySellerId("aaaa");

        assertNull(found);
        assertNotEquals(savedSeller, found);

    }
}

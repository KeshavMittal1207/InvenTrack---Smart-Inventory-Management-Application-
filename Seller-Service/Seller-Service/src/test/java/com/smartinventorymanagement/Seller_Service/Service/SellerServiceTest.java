package com.smartinventorymanagement.Seller_Service.Service;

import com.smartinventorymanagement.Seller_Service.Model.Seller;
import com.smartinventorymanagement.Seller_Service.Repository.SellerRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SellerServiceTest {

    @InjectMocks
    private SellerService sellerService;

    @Mock
    private SellerRepository sellerRepository;

    @Test
    void addSellerTest(){
        Seller seller = Seller.builder()
        .mobile("9876832145")
        .name("John Doe")
        .build();

        when(sellerRepository.save(seller)).thenReturn(
        Seller.builder()
            .sellerId("ABCD1234")  // assume sellerId is generated
            .name("John Doe")
            .mobile("9876832145")
            .build()
        );
        Seller savedSeller = sellerService.addSeller(seller);
        assertNotNull(savedSeller);
        assertNotNull(savedSeller.getSellerId());
        assertEquals("John Doe", savedSeller.getName());
        assertEquals("9876832145", savedSeller.getMobile());
        
    }

    @Test
    void getSellerByIdTest(){
        Seller seller = Seller.builder()
        .mobile("9876832145")
        .name("John Doe")
        .build();

        when(sellerRepository.save(seller)).thenReturn(
        Seller.builder()
            .sellerId("ABCD1234")
            .name("John Doe")
            .mobile("9876832145")
            .build()
        );
        Seller savedSeller = sellerService.addSeller(seller);
        String sellerId = "ABCD1234";
        when(sellerRepository.findBySellerId(sellerId)).thenReturn(savedSeller);
        Optional<Seller> sellerFound = sellerService.getSellerById(sellerId);
        assertNotNull(sellerFound);
        assertEquals(sellerId, sellerFound.get().getSellerId());
    }

    @Test
    void getAllSeller(){
        Seller seller = Seller.builder()
        .mobile("9876832145")
        .name("John Doe")
        .build();
        when(sellerRepository.save(seller)).thenReturn(
            Seller.builder()
            .sellerId("ABCD1234")
            .name("John Doe")
            .mobile("9876832145")
            .build()
        );
        List<Seller> savedSeller = List.of(sellerService.addSeller(seller));
        
        when(sellerRepository.findAll()).thenReturn(
            List.of(Seller.builder()
            .sellerId("ABCD1234")
            .name("John Doe")
            .mobile("9876832145")
            .build()
        ));
        List<Seller> sellers = sellerService.getAllSeller();
        
        assertNotNull(sellers);
        assertEquals(savedSeller, sellers);
    }
}

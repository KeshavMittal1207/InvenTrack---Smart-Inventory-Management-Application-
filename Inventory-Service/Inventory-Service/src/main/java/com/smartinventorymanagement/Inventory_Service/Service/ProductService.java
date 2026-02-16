package com.smartinventorymanagement.Inventory_Service.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartinventorymanagement.Inventory_Service.Enums.ProductStatus;
import com.smartinventorymanagement.Inventory_Service.Model.Product;
import com.smartinventorymanagement.Inventory_Service.Repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product createProduct(Product product){
        product.setStatus(ProductStatus.ACTIVE);
        return productRepository.save(product);
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Product getProduct(Long id){
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public void changeStatus(Long id , ProductStatus status){
        Product product = getProduct(id);
        product.setStatus(status);
        productRepository.save(product);
    }

    public boolean isProductActive(Long productId){
        return productRepository.existsByProductIdAndStatus(productId, ProductStatus.ACTIVE);
    }
}

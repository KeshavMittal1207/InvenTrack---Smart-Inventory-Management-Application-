package com.smartinventorymanagement.Inventory_Service.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smartinventorymanagement.Inventory_Service.Enums.ProductStatus;
import com.smartinventorymanagement.Inventory_Service.Model.Product;
import com.smartinventorymanagement.Inventory_Service.Service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PreAuthorize("hasRole('SHOP_OWNER')")
    @PostMapping("/add-product")
    public ResponseEntity<Product> createProduct(@RequestBody Product product){
        return ResponseEntity.ok(productService.createProduct(product));
    }
    
    @PreAuthorize("hasRole('STAFF') or hasRole('SHOP_OWNER')")
    @GetMapping("get-all-products")
    public ResponseEntity<List<Product>> getAll() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @PreAuthorize("hasRole('STAFF') or hasRole('SHOP_OWNER')")
    @GetMapping("/{id}")
    public ResponseEntity<Product> get(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProduct(id));
    }

    @PreAuthorize("hasRole('STAFF') or hasRole('SHOP_OWNER')")
    @PutMapping("/{id}/status")
    public ResponseEntity<String> updateStatus(
            @PathVariable Long id,
            @RequestParam ProductStatus status
    ) {
        productService.changeStatus(id, status);
        return ResponseEntity.ok("Status updated");
    }

    @PreAuthorize("hasRole('STAFF') or hasRole('SHOP_OWNER')")
    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> exists(@PathVariable Long id) {
        return ResponseEntity.ok(productService.isProductActive(id));
    }
}

package org.example.sellingexchangeplatform.controller;

import org.example.sellingexchangeplatform.dto.request.ProductRequestDTO;
import org.example.sellingexchangeplatform.dto.response.ProductResponseDTO;
import org.example.sellingexchangeplatform.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/create/{sellerId}")
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody ProductRequestDTO productRequestDTO, @PathVariable Long sellerId) {
        ProductResponseDTO productResponseDTO = productService.createProduct(productRequestDTO, sellerId);
        return ResponseEntity.ok(productResponseDTO);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable Long productId, @RequestBody ProductRequestDTO productRequestDTO) {
        ProductResponseDTO productResponseDTO = productService.updateProduct(productId, productRequestDTO);
        return ResponseEntity.ok(productResponseDTO);
    }


    @GetMapping("/products/{userId}")
    public ResponseEntity<List<ProductResponseDTO>> getUserActiveProducts(@PathVariable Long userId) {
        List<ProductResponseDTO> products = productService.getUserActiveProducts(userId);
        return ResponseEntity.ok(products);

    }

    @PutMapping("/sell/{productId}/{buyerId}")
    public ResponseEntity<Void> sellProduct(@PathVariable Long productId, @PathVariable Long buyerId) {
        productService.sellProduct(productId, buyerId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id) {
        ProductResponseDTO productResponseDTO = productService.getProductById(id);
        return ResponseEntity.ok(productResponseDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        List<ProductResponseDTO> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }
}

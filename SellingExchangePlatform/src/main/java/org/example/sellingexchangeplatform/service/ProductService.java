package org.example.sellingexchangeplatform.service;

import org.example.sellingexchangeplatform.dto.request.ProductRequestDTO;
import org.example.sellingexchangeplatform.dto.response.ProductResponseDTO;
import org.example.sellingexchangeplatform.mapper.ProductMapper;

import java.util.List;

public interface ProductService {

    ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO, Long sellerId);

    ProductResponseDTO updateProduct(Long productId, ProductRequestDTO productRequestDTO);

    void sellProduct(Long productId, Long buyerId);

    ProductResponseDTO getProductById(Long id);

    List<ProductResponseDTO> getAllProducts();

    List<ProductResponseDTO> getUserActiveProducts(Long userId);

}

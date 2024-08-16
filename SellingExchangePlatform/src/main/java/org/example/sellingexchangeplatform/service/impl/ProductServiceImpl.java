package org.example.sellingexchangeplatform.service.impl;

import org.example.sellingexchangeplatform.Enum.ProductType;
import org.example.sellingexchangeplatform.dto.request.ProductRequestDTO;
import org.example.sellingexchangeplatform.dto.response.ProductResponseDTO;
import org.example.sellingexchangeplatform.entity.Product;
import org.example.sellingexchangeplatform.entity.User;
import org.example.sellingexchangeplatform.exception.BadRequestException;
import org.example.sellingexchangeplatform.exception.ExceptionMessage;
import org.example.sellingexchangeplatform.exception.NotFoundException;
import org.example.sellingexchangeplatform.mapper.ProductMapper;
import org.example.sellingexchangeplatform.repository.ProductRepository;
import org.example.sellingexchangeplatform.repository.UserRepository;
import org.example.sellingexchangeplatform.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO, Long sellerId) {
        User seller = userRepository.findById(sellerId)
                .orElseThrow(() -> new NotFoundException(String.format(ExceptionMessage.USER_NOT_FOUND.getMessage(), sellerId)));

        Product product = new Product();
        product.setName(productRequestDTO.getName());
        product.setDescription(productRequestDTO.getDescription());
        product.setPrice(productRequestDTO.getPrice());
        product.setProductType(ProductType.valueOf(productRequestDTO.getProductType()));
        product.setSeller(seller);

        Product savedProduct = productRepository.save(product);

        return productMapper.toDto(savedProduct);
    }

    @Override
    public ProductResponseDTO updateProduct(Long productId, ProductRequestDTO productRequestDTO) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(String.format(ExceptionMessage.PRODUCT_NOT_FOUND.getMessage(), productId)));

        product.setName(productRequestDTO.getName());
        product.setDescription(productRequestDTO.getDescription());
        product.setPrice(productRequestDTO.getPrice());
        product.setProductType(ProductType.valueOf(productRequestDTO.getProductType()));

        Product updatedProduct = productRepository.save(product);

        return productMapper.toDto(updatedProduct);
    }

    @Override
    public void sellProduct(Long productId, Long buyerId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(String.format(ExceptionMessage.PRODUCT_NOT_FOUND.getMessage(), productId)));
        User buyer = userRepository.findById(buyerId)
                .orElseThrow(() -> new NotFoundException(String.format(ExceptionMessage.USER_NOT_FOUND.getMessage(), buyerId)));

        if (product.getSeller().getId().equals(buyerId)) {
            throw new BadRequestException("Öz məhsulunuzu satın ala bilməzsiniz.");
        }

        if (product.getProductType() == ProductType.EXCHANGE) {
            throw new BadRequestException("Bu məhsul yalnız barter üçün mövcuddur.");
        }

        if (buyer.getBalance() < product.getPrice()) {
            throw new BadRequestException(String.format(ExceptionMessage.INSUFFICIENT_BALANCE.getMessage(), buyerId));
        }

        buyer.setBalance(buyer.getBalance() - product.getPrice());
        product.getSeller().setBalance(product.getSeller().getBalance() + product.getPrice());

        product.setBuyer(buyer);
        product.setIsSold(true);

        productRepository.save(product);
        userRepository.save(buyer);
        userRepository.save(product.getSeller());
    }

    @Override
    public ProductResponseDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(ExceptionMessage.PRODUCT_NOT_FOUND.getMessage(), id)));
        return productMapper.toDto(product);
    }

    @Override
    public List<ProductResponseDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponseDTO> getUserActiveProducts(Long userId) {
        List<Product> products = productRepository.findBySellerIdAndIsSoldFalseAndExchangeProductIdIsNull(userId);

        return products.stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }


}
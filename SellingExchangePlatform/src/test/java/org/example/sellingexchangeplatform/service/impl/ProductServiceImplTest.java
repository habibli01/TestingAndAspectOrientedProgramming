package org.example.sellingexchangeplatform.service.impl;

import org.example.sellingexchangeplatform.Enum.ProductType;
import org.example.sellingexchangeplatform.dto.request.ProductRequestDTO;
import org.example.sellingexchangeplatform.dto.response.ProductResponseDTO;
import org.example.sellingexchangeplatform.entity.Product;
import org.example.sellingexchangeplatform.entity.User;
import org.example.sellingexchangeplatform.exception.BadRequestException;
import org.example.sellingexchangeplatform.mapper.ProductMapper;
import org.example.sellingexchangeplatform.repository.ProductRepository;
import org.example.sellingexchangeplatform.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProduct() {
        ProductRequestDTO requestDTO = new ProductRequestDTO();
        requestDTO.setName("Test Product");
        requestDTO.setDescription("Test Description");
        requestDTO.setPrice(100.0);
        requestDTO.setProductType("SALE");

        User seller = new User();
        seller.setId(1L);

        Product product = new Product();
        product.setId(1L);
        product.setName(requestDTO.getName());
        product.setDescription(requestDTO.getDescription());
        product.setPrice(requestDTO.getPrice());
        product.setProductType(ProductType.SALE);
        product.setSeller(seller);

        ProductResponseDTO productResponseDTO = new ProductResponseDTO();

        when(userRepository.findById(1L)).thenReturn(Optional.of(seller));
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(productMapper.toDto(any(Product.class))).thenReturn(productResponseDTO);

        ProductResponseDTO responseDTO = productService.createProduct(requestDTO, 1L);

        assertNotNull(responseDTO);
        verify(productRepository, times(1)).save(any(Product.class));
        verify(productMapper, times(1)).toDto(any(Product.class));
    }

    @Test
    void testUpdateProduct() {
        ProductRequestDTO requestDTO = new ProductRequestDTO();
        requestDTO.setName("Updated Product");
        requestDTO.setDescription("Updated Description");
        requestDTO.setPrice(150.0);
        requestDTO.setProductType("SALE");

        Product existingProduct = new Product();
        existingProduct.setId(1L);
        existingProduct.setName("Old Product");
        existingProduct.setDescription("Old Description");
        existingProduct.setPrice(100.0);
        existingProduct.setProductType(ProductType.SALE);

        ProductResponseDTO productResponseDTO = new ProductResponseDTO();

        when(productRepository.findById(1L)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(existingProduct);
        when(productMapper.toDto(any(Product.class))).thenReturn(productResponseDTO);

        ProductResponseDTO responseDTO = productService.updateProduct(1L, requestDTO);

        assertNotNull(responseDTO);
        assertEquals(requestDTO.getName(), existingProduct.getName());
        assertEquals(requestDTO.getDescription(), existingProduct.getDescription());
        assertEquals(requestDTO.getPrice(), existingProduct.getPrice());
        verify(productRepository, times(1)).save(existingProduct);
        verify(productMapper, times(1)).toDto(any(Product.class));
    }

    @Test
    void testSellProduct() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Product");
        product.setPrice(100.0);
        product.setProductType(ProductType.SALE);
        product.setSeller(new User());
        product.getSeller().setId(1L);
        product.getSeller().setBalance(200.0);

        User buyer = new User();
        buyer.setId(2L);
        buyer.setBalance(150.0);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(userRepository.findById(2L)).thenReturn(Optional.of(buyer));

        productService.sellProduct(1L, 2L);

        assertTrue(product.getIsSold());
        assertEquals(50.0, buyer.getBalance());
        assertEquals(300.0, product.getSeller().getBalance());
        verify(productRepository, times(1)).save(product);
        verify(userRepository, times(1)).save(buyer);
        verify(userRepository, times(1)).save(product.getSeller());
    }

    @Test
    void testSellProductThrowsExceptionIfBuyerIsSeller() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Product");
        product.setPrice(100.0);
        product.setProductType(ProductType.SALE);
        product.setSeller(new User());
        product.getSeller().setId(1L);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(userRepository.findById(1L)).thenReturn(Optional.of(product.getSeller()));

        assertThrows(BadRequestException.class, () -> productService.sellProduct(1L, 1L));
    }

    @Test
    void testSellProductThrowsExceptionIfProductIsForExchange() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Product");
        product.setPrice(100.0);
        product.setProductType(ProductType.EXCHANGE);
        product.setSeller(new User());
        product.getSeller().setId(1L);

        User buyer = new User();
        buyer.setId(2L);
        buyer.setBalance(150.0);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(userRepository.findById(2L)).thenReturn(Optional.of(buyer));

        assertThrows(BadRequestException.class, () -> productService.sellProduct(1L, 2L));
    }

    @Test
    void testSellProductThrowsExceptionIfBuyerHasInsufficientBalance() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Product");
        product.setPrice(100.0);
        product.setProductType(ProductType.SALE);
        product.setSeller(new User());
        product.getSeller().setId(1L);

        User buyer = new User();
        buyer.setId(2L);
        buyer.setBalance(50.0);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(userRepository.findById(2L)).thenReturn(Optional.of(buyer));

        assertThrows(BadRequestException.class, () -> productService.sellProduct(1L, 2L));
    }

    @Test
    void testGetProductById() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Product");

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productMapper.toDto(any(Product.class))).thenReturn(new ProductResponseDTO());

        ProductResponseDTO responseDTO = productService.getProductById(1L);

        assertNotNull(responseDTO);
        verify(productRepository, times(1)).findById(1L);
        verify(productMapper, times(1)).toDto(any(Product.class));
    }

    @Test
    void testGetAllProducts() {
        when(productRepository.findAll()).thenReturn(List.of(new Product()));
        when(productMapper.toDto(any(Product.class))).thenReturn(new ProductResponseDTO());

        List<ProductResponseDTO> responseDTOs = productService.getAllProducts();

        assertNotNull(responseDTOs);
        assertFalse(responseDTOs.isEmpty());
        verify(productRepository, times(1)).findAll();
        verify(productMapper, times(1)).toDto(any(Product.class));
    }

    @Test
    void testGetUserActiveProducts() {
        User seller = new User();
        seller.setId(1L);

        Product product = new Product();
        product.setId(1L);
        product.setName("Product");
        product.setSeller(seller);
        product.setIsSold(false);

        when(productRepository.findBySellerIdAndIsSoldFalseAndExchangeProductIdIsNull(1L))
                .thenReturn(List.of(product));
        when(productMapper.toDto(any(Product.class))).thenReturn(new ProductResponseDTO());

        List<ProductResponseDTO> responseDTOs = productService.getUserActiveProducts(1L);

        assertNotNull(responseDTOs);
        assertFalse(responseDTOs.isEmpty());
        verify(productRepository, times(1))
                .findBySellerIdAndIsSoldFalseAndExchangeProductIdIsNull(1L);
        verify(productMapper, times(1)).toDto(any(Product.class));
    }
}

package org.example.sellingexchangeplatform.repository;

import org.example.sellingexchangeplatform.Enum.ProductType;
import org.example.sellingexchangeplatform.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findBySellerId(Long sellerId);

    List<Product> findByBuyerId(Long buyerId);

    List<Product> findByProductTypeAndIsSoldFalse(ProductType productType);

    List<Product> findBySellerIdAndIsSoldFalseAndExchangeProductIdIsNull(Long sellerId);

}

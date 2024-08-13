package org.example.sellingexchangeplatform.repository;

import org.example.sellingexchangeplatform.entity.UserProductList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserProductListRepository extends JpaRepository<UserProductList, Long> {
    List<UserProductList> findByUserId(Long userId);

    Optional<UserProductList> findByUserIdAndProductId(Long userId, Long productId);
}

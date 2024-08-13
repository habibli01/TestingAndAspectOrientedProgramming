package org.example.sellingexchangeplatform.service;

import org.example.sellingexchangeplatform.dto.response.UserProductListResponseDTO;

import java.util.List;

public interface UserProductListService {

    List<UserProductListResponseDTO> getUserProductList(Long userId);

    void addProductToList(Long userId, Long productId);

    void removeProductFromList(Long userId, Long productId);
}

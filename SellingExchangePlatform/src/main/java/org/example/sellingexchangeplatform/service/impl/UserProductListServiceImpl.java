package org.example.sellingexchangeplatform.service.impl;

import org.example.sellingexchangeplatform.dto.response.UserProductListResponseDTO;
import org.example.sellingexchangeplatform.entity.Product;
import org.example.sellingexchangeplatform.entity.User;
import org.example.sellingexchangeplatform.entity.UserProductList;
import org.example.sellingexchangeplatform.exception.ExceptionMessage;
import org.example.sellingexchangeplatform.exception.NotFoundException;
import org.example.sellingexchangeplatform.mapper.UserProductListMapper;
import org.example.sellingexchangeplatform.repository.ProductRepository;
import org.example.sellingexchangeplatform.repository.UserProductListRepository;
import org.example.sellingexchangeplatform.repository.UserRepository;
import org.example.sellingexchangeplatform.service.UserProductListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserProductListServiceImpl implements UserProductListService {

    @Autowired
    private UserProductListRepository userProductListRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserProductListMapper userProductListMapper;

    @Override
    public List<UserProductListResponseDTO> getUserProductList(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException(String.format(ExceptionMessage.USER_NOT_FOUND.getMessage(), userId));
        }

        List<UserProductList> userProductLists = userProductListRepository.findByUserId(userId);
        return userProductLists.stream()
                .map(userProductListMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void addProductToList(Long userId, Long productId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format(ExceptionMessage.USER_NOT_FOUND.getMessage(), userId)));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(String.format(ExceptionMessage.PRODUCT_NOT_FOUND.getMessage(), productId)));

        UserProductList userProductList = new UserProductList();
        userProductList.setUser(user);
        userProductList.setProduct(product);
        userProductList.setAddedDate(LocalDateTime.now());

        userProductListRepository.save(userProductList);
    }

    @Override
    public void removeProductFromList(Long userId, Long productId) {
        UserProductList userProductList = userProductListRepository.findByUserIdAndProductId(userId, productId)
                .orElseThrow(() -> new NotFoundException(String.format(ExceptionMessage.NOT_FOUND.getMessage(), "User product list")));

        userProductListRepository.delete(userProductList);
    }
}

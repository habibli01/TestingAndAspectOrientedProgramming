package org.example.sellingexchangeplatform.controller;

import org.example.sellingexchangeplatform.dto.response.UserProductListResponseDTO;
import org.example.sellingexchangeplatform.service.UserProductListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-products")
public class UserProductListController {

    @Autowired
    private UserProductListService userProductListService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<UserProductListResponseDTO>> getUserProductList(@PathVariable Long userId) {
        List<UserProductListResponseDTO> userProductList = userProductListService.getUserProductList(userId);
        return ResponseEntity.ok(userProductList);
    }

    @PostMapping("/{userId}/add/{productId}")
    public ResponseEntity<Void> addProductToList(@PathVariable Long userId, @PathVariable Long productId) {
        userProductListService.addProductToList(userId, productId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}/remove/{productId}")
    public ResponseEntity<Void> removeProductFromList(@PathVariable Long userId, @PathVariable Long productId) {
        userProductListService.removeProductFromList(userId, productId);
        return ResponseEntity.ok().build();
    }
}

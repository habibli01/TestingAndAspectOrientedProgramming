package org.example.sellingexchangeplatform.controller;

import org.example.sellingexchangeplatform.dto.response.OrderResponseDTO;
import org.example.sellingexchangeplatform.Enum.OrderType;
import org.example.sellingexchangeplatform.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/{productId}/{buyerId}")
    public ResponseEntity<OrderResponseDTO> createOrder(
            @PathVariable Long productId,
            @PathVariable Long buyerId,
            @RequestParam(required = false) Long exchangeProductId,
            @RequestParam OrderType orderType) {
        OrderResponseDTO orderResponseDTO = orderService.createOrder(productId, buyerId, exchangeProductId, orderType);
        return ResponseEntity.ok(orderResponseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable Long id) {
        OrderResponseDTO orderResponseDTO = orderService.getOrderById(id);
        return ResponseEntity.ok(orderResponseDTO);
    }
}

package org.example.sellingexchangeplatform.service;

import org.example.sellingexchangeplatform.dto.response.OrderResponseDTO;
import org.example.sellingexchangeplatform.Enum.OrderType;

public interface OrderService {
    OrderResponseDTO createOrder(Long productId, Long buyerId, Long exchangeProductId, OrderType orderType);
    OrderResponseDTO getOrderById(Long id);
}


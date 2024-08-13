package org.example.sellingexchangeplatform.service.impl;

import org.example.sellingexchangeplatform.Enum.OrderType;
import org.example.sellingexchangeplatform.dto.response.OrderResponseDTO;
import org.example.sellingexchangeplatform.entity.Order;
import org.example.sellingexchangeplatform.entity.Product;
import org.example.sellingexchangeplatform.entity.User;
import org.example.sellingexchangeplatform.exception.BadRequestException;
import org.example.sellingexchangeplatform.exception.ExceptionMessage;
import org.example.sellingexchangeplatform.exception.NotFoundException;
import org.example.sellingexchangeplatform.repository.OrderRepository;
import org.example.sellingexchangeplatform.repository.ProductRepository;
import org.example.sellingexchangeplatform.repository.UserRepository;
import org.example.sellingexchangeplatform.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public OrderResponseDTO createOrder(Long productId, Long buyerId, Long exchangeProductId, OrderType orderType) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(String.format(ExceptionMessage.PRODUCT_NOT_FOUND.getMessage(), productId)));
        User buyer = userRepository.findById(buyerId)
                .orElseThrow(() -> new NotFoundException(String.format(ExceptionMessage.USER_NOT_FOUND.getMessage(), buyerId)));

        if (orderType == OrderType.EXCHANGE && exchangeProductId != null) {
            Product exchangeProduct = productRepository.findById(exchangeProductId)
                    .orElseThrow(() -> new NotFoundException(String.format(ExceptionMessage.PRODUCT_NOT_FOUND.getMessage(), exchangeProductId)));

            // Qiymətləri yoxlayın
            if (!product.getPrice().equals(exchangeProduct.getPrice())) {
                throw new BadRequestException("Məhsulların qiymətləri eyni deyil.");
            }

            // Məhsul sahiblərini dəyişdirin
            User originalSeller = product.getSeller();
            User exchangeSeller = exchangeProduct.getSeller();

            product.setSeller(exchangeSeller);
            exchangeProduct.setSeller(originalSeller);

            productRepository.save(product);
            productRepository.save(exchangeProduct);
        } else if (orderType == OrderType.SALE) {
            // Normal satış məntiqi
            if (buyer.getBalance() < product.getPrice()) {
                throw new BadRequestException("Balans kifayət qədər deyil.");
            }

            buyer.setBalance(buyer.getBalance() - product.getPrice());
            product.getSeller().setBalance(product.getSeller().getBalance() + product.getPrice());

            product.setBuyer(buyer);
            product.setIsSold(true);

            productRepository.save(product);
            userRepository.save(buyer);
            userRepository.save(product.getSeller());
        } else {
            throw new BadRequestException("Səhv sifariş növü.");
        }

        // Order obyektini yaratmaq
        Order order = new Order();
        order.setProduct(product);
        order.setBuyer(buyer);
        order.setOrderType(orderType);
        order.setOrderDate(LocalDateTime.now());
        order.setIsCompleted(true);

        Order savedOrder = orderRepository.save(order);

        return mapToOrderResponseDTO(savedOrder);
    }

    @Override
    public OrderResponseDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(ExceptionMessage.NOT_FOUND.getMessage(), id)));
        return mapToOrderResponseDTO(order);
    }

    private OrderResponseDTO mapToOrderResponseDTO(Order order) {
        OrderResponseDTO responseDTO = new OrderResponseDTO();
        responseDTO.setId(order.getId());
        responseDTO.setProductName(order.getProduct().getName());
        responseDTO.setBuyerUsername(order.getBuyer().getUsername());
        responseDTO.setOrderDate(order.getOrderDate());
        responseDTO.setIsCompleted(order.getIsCompleted());
        return responseDTO;
    }
}

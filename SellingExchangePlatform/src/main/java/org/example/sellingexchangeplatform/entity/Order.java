package org.example.sellingexchangeplatform.entity;

import org.example.sellingexchangeplatform.Enum.OrderType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "orders")

public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "buyer_id", nullable = false)
    User buyer;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    Product product;

    @Column(name = "exchange_product_id")
    Long exchangeProductId;  // Mübadilə üçün seçilmiş məhsulun ID-si

    @Enumerated(EnumType.STRING)
    OrderType orderType; // Satış və ya mübadilə

    @Column(nullable = false)
    LocalDateTime orderDate;

    Boolean isCompleted = false;

    @Column(nullable = false, updatable = false)
    LocalDateTime createdDate;

    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDateTime.now();
    }
}

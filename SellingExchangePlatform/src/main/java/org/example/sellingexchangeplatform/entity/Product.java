package org.example.sellingexchangeplatform.entity;

import org.example.sellingexchangeplatform.Enum.ProductType;
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
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String description;

    @Enumerated(EnumType.STRING)
    ProductType productType; // Satış üçün və ya mübadilə üçün

    @Column(nullable = false)
    Double price;

    Long exchangeProductId;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    User seller;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    User buyer;

    Boolean isSold = false;

    @Column(nullable = false, updatable = false)
    LocalDateTime createdDate;

    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDateTime.now();
    }
}

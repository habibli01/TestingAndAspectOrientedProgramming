package org.example.sellingexchangeplatform.entity;

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
@Table(name = "user_product_list")
public class UserProductList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    Product product;

    LocalDateTime addedDate;

    @Column(nullable = false, updatable = false)
    LocalDateTime createdDate;

    @PrePersist
    protected void onCreate() {
        this.addedDate = LocalDateTime.now();
        this.createdDate = LocalDateTime.now();
    }
}

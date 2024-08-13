package org.example.sellingexchangeplatform.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    Product product;

    @Column(nullable = false)
    String content;

    @Column(nullable = false)
    int rating;

    @Column(name = "date", nullable = false)
    LocalDate date;

    @Column(nullable = false, updatable = false)
    LocalDateTime createdDate;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    List<Replay> replays;

    @PrePersist
    protected void onCreate() {
        this.date = LocalDate.now();
        this.createdDate = LocalDateTime.now();
    }
}

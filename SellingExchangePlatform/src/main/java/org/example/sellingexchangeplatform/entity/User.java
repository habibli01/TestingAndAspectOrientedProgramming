package org.example.sellingexchangeplatform.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Size(min = 5, max = 12)
    @Column(unique = true, nullable = false)
    String username;

    @Size(min = 3, max = 20)
    @Column(nullable = false)
    String firstName;

    @Size(min = 5, max = 25)
    String lastName;

    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
            message = "Şifrə ən az 8 simvol, 1 böyük hərf, 1 kiçik hərf, 1 rəqəm və 1 xüsusi simvol içərməlidir"
    )
    @Column(nullable = false)
    String password;

    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "Email forması düzgün deyil")
    @Column(unique = true, nullable = false)
    String email;

    @Column(nullable = false)
    Double balance = 0.0;  // İstifadəçinin balansı

    @Column(nullable = false, updatable = false)
    LocalDateTime registryDate;

    @Column(nullable = false, updatable = false)
    LocalDateTime createdDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    Set<Role> roles;

    @OneToMany(mappedBy = "seller")
    Set<Product> products;

    @OneToMany(mappedBy = "user")
    Set<UserProductList> userProductLists;

    @PrePersist
    protected void onCreate() {
        this.registryDate = LocalDateTime.now();
        this.createdDate = LocalDateTime.now();
    }

}
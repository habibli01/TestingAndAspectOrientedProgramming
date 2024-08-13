package org.example.sellingexchangeplatform.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResponseDTO {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private Double balance;
    private LocalDateTime registryDate;
    private LocalDateTime createdDate;
    private Set<String> roles;
}

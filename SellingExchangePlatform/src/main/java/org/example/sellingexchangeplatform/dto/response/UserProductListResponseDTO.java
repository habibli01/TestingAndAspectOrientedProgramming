package org.example.sellingexchangeplatform.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserProductListResponseDTO {

    private Long id;
    private String productName;
    private LocalDateTime addedDate;
    private Long productId;
    }


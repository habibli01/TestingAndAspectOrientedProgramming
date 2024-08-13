package org.example.sellingexchangeplatform.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderRequestDTO {

    private Long productId;
    private String orderType; // SALE or EXCHANGE
}

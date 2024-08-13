package org.example.sellingexchangeplatform.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.sellingexchangeplatform.Enum.OrderType;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderResponseDTO {

    private Long id;
    private String buyerUsername;
    private String productName;
    private OrderType orderType;
    private LocalDateTime orderDate;
    private Boolean isCompleted;
}

package org.example.sellingexchangeplatform.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductRequestDTO {

    private String name;
    private String description;
    private Double price;
    private String productType; // SALE or EXCHANGE
}

package org.example.sellingexchangeplatform.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReviewRequestDTO {

    private Long productId;
    private String content;
    private int rating;
}

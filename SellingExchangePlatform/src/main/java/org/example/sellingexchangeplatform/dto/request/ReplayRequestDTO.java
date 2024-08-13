package org.example.sellingexchangeplatform.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReplayRequestDTO {

    private Long reviewId;
    private String content;
}

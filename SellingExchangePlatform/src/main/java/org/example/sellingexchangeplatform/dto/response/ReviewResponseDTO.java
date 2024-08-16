package org.example.sellingexchangeplatform.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReviewResponseDTO {

    private Long id;
    private String username;
    private String productName;
    private String content;
    private int rating;
    private LocalDateTime createdDate;
    private List<ReplayResponseDTO> replays;
}

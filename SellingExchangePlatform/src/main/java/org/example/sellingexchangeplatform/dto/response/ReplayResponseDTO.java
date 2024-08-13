package org.example.sellingexchangeplatform.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReplayResponseDTO {

    private Long id;
    private String username;
    private String content;
    private LocalDateTime createdDate;
}

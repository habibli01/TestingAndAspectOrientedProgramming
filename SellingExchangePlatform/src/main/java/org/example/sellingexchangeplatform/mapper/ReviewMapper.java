package org.example.sellingexchangeplatform.mapper;

import org.example.sellingexchangeplatform.dto.response.ReviewResponseDTO;
import org.example.sellingexchangeplatform.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ReplayMapper.class})
public interface ReviewMapper {

    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "product.name", target = "productName")
    ReviewResponseDTO toDto(Review review);
}

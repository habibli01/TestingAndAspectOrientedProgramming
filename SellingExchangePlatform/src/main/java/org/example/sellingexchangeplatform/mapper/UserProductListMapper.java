package org.example.sellingexchangeplatform.mapper;

import org.example.sellingexchangeplatform.dto.response.UserProductListResponseDTO;
import org.example.sellingexchangeplatform.entity.UserProductList;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserProductListMapper {
    @Mappings({
            @Mapping(source = "product.id", target = "productId"),
            @Mapping(source = "product.name", target = "productName")
    })
    UserProductListResponseDTO toDto(UserProductList userProductList);
}

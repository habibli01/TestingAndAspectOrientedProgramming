package org.example.sellingexchangeplatform.mapper;

import org.example.sellingexchangeplatform.dto.response.ProductResponseDTO;
import org.example.sellingexchangeplatform.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mappings({
            @Mapping(source = "seller.username", target = "sellerName"),
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "description", target = "description"),
            @Mapping(source = "price", target = "price"),
            @Mapping(source = "productType", target = "productType"),
            @Mapping(source = "isSold", target = "isSold"),
            @Mapping(source = "createdDate", target = "createdDate")
    })

    ProductResponseDTO toDto(Product product);
}

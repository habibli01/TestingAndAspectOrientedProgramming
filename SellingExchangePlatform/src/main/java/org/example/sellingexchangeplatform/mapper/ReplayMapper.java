package org.example.sellingexchangeplatform.mapper;

import org.example.sellingexchangeplatform.dto.response.ReplayResponseDTO;
import org.example.sellingexchangeplatform.entity.Replay;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReplayMapper {

    @Mapping(source = "user.username", target = "username")
    ReplayResponseDTO toDto(Replay replay);
}

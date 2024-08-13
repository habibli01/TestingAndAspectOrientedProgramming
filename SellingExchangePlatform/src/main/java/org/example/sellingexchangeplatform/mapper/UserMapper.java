package org.example.sellingexchangeplatform.mapper;

import org.example.sellingexchangeplatform.dto.response.UserResponseDTO;
import org.example.sellingexchangeplatform.entity.Role;
import org.example.sellingexchangeplatform.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "roles", source = "roles", qualifiedByName = "mapRoles")
    UserResponseDTO toDto(User user);

    @Named("mapRoles")
    default Set<String> mapRoles(Set<Role> roles) {
        return roles.stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
    }
}

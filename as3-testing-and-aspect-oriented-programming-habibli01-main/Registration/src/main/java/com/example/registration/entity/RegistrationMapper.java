package com.example.registration.entity;

import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
@Component
public interface RegistrationMapper {

    RegistrationMapper INSTANCE = Mappers.getMapper(RegistrationMapper.class);

    RegistrationDTO toDTO(Registration registration);

    Registration toEntity(RegistrationDTO registrationDTO);
}
package com.example.registration.service;

import com.example.registration.entity.RegistrationDTO;

import java.util.List;

public interface RegistrationService {
    List<RegistrationDTO> list();

    void delete(Long id);

    void register(RegistrationDTO registrationDTO);

    void update(Long id, RegistrationDTO registrationDTO);

    void patch(Long id, RegistrationDTO registrationDTO);
}

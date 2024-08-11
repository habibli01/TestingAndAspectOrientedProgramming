package com.example.registration.service;

import com.example.registration.entity.Registration;
import com.example.registration.entity.RegistrationDTO;
import com.example.registration.entity.RegistrationMapper;
import com.example.registration.repository.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    private  RegistrationRepository registrationRepository;
    @Autowired
    private  RegistrationMapper registrationMapper;


    @Override
    public List<RegistrationDTO> list() {
        return registrationRepository.findAll().stream()
                .map(registrationMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
}

    @Override
    public void delete(Long id) {
        registrationRepository.deleteById(id);
    }

    @Override
    public void register(RegistrationDTO registrationDTO) {
        Registration registration = registrationMapper.toEntity(registrationDTO);
        registrationRepository.save(registration);
    }

    @Override
    public void update(Long id, RegistrationDTO registrationDTO) {
        Registration existingRegistration = registrationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registration not found"));

        existingRegistration.setFirstName(registrationDTO.getFirstName());
        existingRegistration.setLastName(registrationDTO.getLastName());
        existingRegistration.setPassword(registrationDTO.getPassword());
        existingRegistration.setRole(registrationDTO.getRole());

        registrationRepository.save(existingRegistration);
    }

    @Override
    public void patch(Long id, RegistrationDTO registrationDTO) {
        Registration existingRegistration = registrationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registration not found"));

        if (registrationDTO.getFirstName() != null) {
            existingRegistration.setFirstName(registrationDTO.getFirstName());
        }
        if (registrationDTO.getLastName() != null) {
            existingRegistration.setLastName(registrationDTO.getLastName());
        }
        if (registrationDTO.getPassword() != null) {
            existingRegistration.setPassword(registrationDTO.getPassword());
        }
        if (registrationDTO.getRole() != null) {
            existingRegistration.setRole(registrationDTO.getRole());
        }

        registrationRepository.save(existingRegistration);
    }

}

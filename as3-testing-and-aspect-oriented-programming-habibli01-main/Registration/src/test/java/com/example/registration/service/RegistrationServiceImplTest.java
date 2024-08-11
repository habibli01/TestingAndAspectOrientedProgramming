package com.example.registration.service;

import com.example.registration.entity.Registration;
import com.example.registration.entity.RegistrationDTO;
import com.example.registration.entity.RegistrationMapper;
import com.example.registration.repository.RegistrationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RegistrationServiceImplTest {

    @Mock
    private RegistrationRepository registrationRepository;

    @Mock
    private RegistrationMapper registrationMapper;

    @InjectMocks
    private RegistrationServiceImpl registrationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testList() {
        // Given
        List<Registration> registrations = new ArrayList<>();
        registrations.add(new Registration());
        when(registrationRepository.findAll()).thenReturn(registrations);

        // When
        List<RegistrationDTO> result = registrationService.list();

        // Then
        assertEquals(1, result.size());
    }

    @Test
    void testDelete() {
        // Given
        Long id = 1L;

        // When
        registrationService.delete(id);

        // Then
        verify(registrationRepository, times(1)).deleteById(id);
    }

    @Test
    void testRegister() {
        // Given
        RegistrationDTO registrationDTO = new RegistrationDTO();
        Registration registration = new Registration();
        when(registrationMapper.toEntity(registrationDTO)).thenReturn(registration);

        // When
        registrationService.register(registrationDTO);

        // Then
        verify(registrationRepository, times(1)).save(registration);
    }

    @Test
    void testUpdate() {
        // Given
        Long id = 1L;
        RegistrationDTO registrationDTO = new RegistrationDTO();
        registrationDTO.setFirstName("John");
        registrationDTO.setLastName("Doe");
        registrationDTO.setPassword("password");
        registrationDTO.setRole("USER");

        Registration existingRegistration = new Registration();
        when(registrationRepository.findById(id)).thenReturn(Optional.of(existingRegistration));

        // When
        registrationService.update(id, registrationDTO);

        // Then
        assertEquals("John", existingRegistration.getFirstName());
        assertEquals("Doe", existingRegistration.getLastName());
        assertEquals("password", existingRegistration.getPassword());
        assertEquals("USER", existingRegistration.getRole());
        verify(registrationRepository, times(1)).save(existingRegistration);
    }

    @Test
    void testPatch() {
        // Given
        Long id = 1L;
        RegistrationDTO registrationDTO = new RegistrationDTO();
        registrationDTO.setFirstName("John");

        Registration existingRegistration = new Registration();
        when(registrationRepository.findById(id)).thenReturn(Optional.of(existingRegistration));

        // When
        registrationService.patch(id, registrationDTO);

        // Then
        assertEquals("John", existingRegistration.getFirstName());
        verify(registrationRepository, times(1)).save(existingRegistration);
    }
}

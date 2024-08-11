package com.example.registration.controller;

import com.example.registration.entity.RegistrationDTO;
import com.example.registration.service.RegistrationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @GetMapping("/list")
    public ResponseEntity<List<RegistrationDTO>> listSubways() {
        List<RegistrationDTO> regs = registrationService.list();
        return ResponseEntity.ok(regs);
    }

    @DeleteMapping("/{id}")
    public void deleteRegistration(@PathVariable("id") Long id) {
        registrationService.delete(id);
    }

    @PostMapping("/")
    public void register(@RequestBody @Valid RegistrationDTO registrationDTO) {
        registrationService.register(registrationDTO);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable("id") Long id, @RequestBody @Valid RegistrationDTO registrationDTO) {
        registrationService.update(id, registrationDTO);
    }

    @PatchMapping("/{id}")
    public void patch(@PathVariable("id") Long id, @RequestBody RegistrationDTO registrationDTO) {
        registrationService.patch(id, registrationDTO);
    }

}

package com.example.redirectregistration.controller;

import com.example.redirectregistration.entity.RegistrationDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/redirect/registration")
@Validated
public class RedirectController {

    private final String baseUrl = "http://localhost:8080/registration";
    private final RestTemplate restTemplate = new RestTemplate();

    public void fixPatch() {
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }

    @GetMapping("/list")
    public List<RegistrationDTO> register() {
        ResponseEntity<List> response = restTemplate.getForEntity(baseUrl + "/list", List.class);
        return response.getBody();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        restTemplate.delete(baseUrl + "/" + id);
    }

    @PostMapping("/")
    public void add(@RequestBody RegistrationDTO registrationDTO) {
        restTemplate.postForEntity(baseUrl + "/", registrationDTO, Void.class);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable("id") Long id, @RequestBody RegistrationDTO registrationDTO) {
        restTemplate.put(baseUrl + "/" + id, registrationDTO);
    }

    @PatchMapping("/{id}")
    public void patch(@PathVariable("id") Long id, @RequestBody RegistrationDTO registrationDTO) {
        fixPatch();
        restTemplate.patchForObject(baseUrl + "/" + id, registrationDTO, Void.class);
    }

}


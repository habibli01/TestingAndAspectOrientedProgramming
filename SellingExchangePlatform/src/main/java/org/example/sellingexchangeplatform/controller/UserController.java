package org.example.sellingexchangeplatform.controller;

import org.example.sellingexchangeplatform.dto.request.UserRequestDTO;
import org.example.sellingexchangeplatform.dto.response.UserResponseDTO;
import org.example.sellingexchangeplatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO registeredUser = userService.registerUser(userRequestDTO);
        return ResponseEntity.ok(registeredUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        UserResponseDTO userResponseDTO = userService.getUserById(id);
        return ResponseEntity.ok(userResponseDTO);
    }

    @PutMapping("/{id}/balance")
    public ResponseEntity<Void> updateBalance(@PathVariable Long id, @RequestParam Double amount) {
        userService.updateBalance(id, amount);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserResponseDTO> getUserByUsername(@PathVariable String username) {
        UserResponseDTO userResponseDTO = userService.getUserByUsername(username);
        return ResponseEntity.ok(userResponseDTO);
    }
}

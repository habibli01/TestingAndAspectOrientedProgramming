package org.example.sellingexchangeplatform.service.impl;

import org.example.sellingexchangeplatform.dto.request.UserRequestDTO;
import org.example.sellingexchangeplatform.dto.response.UserResponseDTO;
import org.example.sellingexchangeplatform.entity.Role;
import org.example.sellingexchangeplatform.entity.User;
import org.example.sellingexchangeplatform.exception.BadRequestException;
import org.example.sellingexchangeplatform.exception.ExceptionMessage;
import org.example.sellingexchangeplatform.exception.NotFoundException;
import org.example.sellingexchangeplatform.mapper.UserMapper;
import org.example.sellingexchangeplatform.repository.RoleRepository;
import org.example.sellingexchangeplatform.repository.UserRepository;
import org.example.sellingexchangeplatform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserResponseDTO registerUser(UserRequestDTO userRequestDTO) {
        if (userRepository.existsByUsername(userRequestDTO.getUsername())) {
            throw new BadRequestException(String.format(ExceptionMessage.CONFLICT.getMessage(), "Username already exists"));
        }

        User user = new User();
        user.setUsername(userRequestDTO.getUsername());
        user.setFirstName(userRequestDTO.getFirstName());
        user.setLastName(userRequestDTO.getLastName());
        user.setEmail(userRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new NotFoundException(String.format(ExceptionMessage.NOT_FOUND.getMessage(), "Role")));
        roles.add(userRole);
        user.setRoles(roles);

        user.setBalance(0.0);

        User savedUser = userRepository.save(user);

        return userMapper.toDto(savedUser);
    }
    @Override
    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(ExceptionMessage.USER_NOT_FOUND.getMessage(), id)));
        return userMapper.toDto(user);
    }

    @Override
    public void updateBalance(Long userId, Double amount) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format(ExceptionMessage.USER_NOT_FOUND.getMessage(), userId)));

        if (amount < 0 && user.getBalance() + amount < 0) {
            throw new BadRequestException(String.format(ExceptionMessage.INSUFFICIENT_BALANCE.getMessage(), userId));
        }

        user.setBalance(user.getBalance() + amount);
        userRepository.save(user);
    }

    @Override
    public UserResponseDTO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(String.format(ExceptionMessage.USER_NOT_FOUND.getMessage(), username)));
        return userMapper.toDto(user);
    }
}

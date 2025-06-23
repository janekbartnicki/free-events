package com.example.backend.service;

import com.example.backend.dto.UserLoginRequestDTO;
import com.example.backend.model.User;
import com.example.backend.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public User registerUser(User user) {
        if (user.getName().isEmpty()) {
            throw new IllegalArgumentException("Username is empty");
        }

        if (user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password is empty");
        }

        if (user.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email is empty");
        }

        if (existsByName(user.getName()) || existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("User already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return usersRepository.save(user);
    }

    public User loginUser(UserLoginRequestDTO userLoginRequestDTO) throws IllegalArgumentException, BadCredentialsException {
        if (userLoginRequestDTO.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email is empty");
        }

        if (userLoginRequestDTO.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password is empty");
        }

        User foundUser = findByEmail(userLoginRequestDTO.getEmail());

        if (foundUser == null || !passwordEncoder.matches(userLoginRequestDTO.getPassword(), foundUser.getPassword())) {
            throw new BadCredentialsException("Invalid email or password");
        }

        return foundUser;
    }

    public User findUserById(Long id) {
        return usersRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "The user with the specified id was not found"));
    }

    private boolean existsByName(String name) {
        return usersRepository.existsByName(name);
    }

    private boolean existsByEmail(String email) {
        return usersRepository.existsByEmail(email);
    }

    private User findByEmail(String email) {
        return usersRepository.findByEmail(email);
    }
}

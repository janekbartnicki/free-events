package com.example.backend.controller;

import com.example.backend.dto.UserLoginRequestDTO;
import com.example.backend.model.User;
import com.example.backend.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class UsersController {

    private final UsersService usersService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        if (user.getName() == null || user.getPassword() == null || user.getEmail() == null) {
            return ResponseEntity.badRequest().body("Missing fields");
        }

        try {
            usersService.registerUser(user);
            return ResponseEntity.ok("Registration was successful");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch(DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserLoginRequestDTO userRequest) {
        if (userRequest.getEmail() == null || userRequest.getPassword() == null) {
            return ResponseEntity.badRequest().body("Missing fields");
        }

        try {
            User loggedInUser = usersService.loginUser(userRequest);
            return ResponseEntity.ok(loggedInUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}

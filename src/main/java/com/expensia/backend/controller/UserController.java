
package com.expensia.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.expensia.backend.entity.User;
import com.expensia.backend.repository.UserRepository;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {

        if (userRepository.findByEmail(user.getEmail()) != null) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Email already registered");
        }

        User savedUser = userRepository.save(user);

        savedUser.setPassword(null);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {

        User existingUser =
                userRepository.findByEmail(user.getEmail());

        if (existingUser == null ||
                !existingUser.getPassword().equals(user.getPassword())) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid email or password");
        }

        existingUser.setPassword(null);

        return ResponseEntity.ok(existingUser);
    }
}

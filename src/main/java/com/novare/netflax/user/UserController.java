package com.novare.netflax.user;

import com.novare.netflax.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;


@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        if (user.getType() == 0) {
            user.setType(2);
        }
        user.setPassword(Encrypt.encryptPassword(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestBody String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new ResourceNotFoundException();
        }
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user) {
        String password = (Encrypt.encryptPassword(user.getPassword()));
        User loginUser = userRepository.findByEmail(user.getEmail());
        if (!Objects.equals(loginUser.getPassword(), password)) {
            throw new Error("Invalid credentials");
        }
        return ResponseEntity.ok(loginUser);
    }
}

package com.novare.netflax.user;

import com.novare.netflax.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
public class UserController {

    @Autowired
    UserRepository helloInterface;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserController(UserRepository helloInterface) {
        this.helloInterface = helloInterface;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        helloInterface.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestBody String email) {
        User user = helloInterface.findByEmail(email);
        if (user == null) {
            throw new ResourceNotFoundException();
        }
        return ResponseEntity.ok().body(user);
    }
}

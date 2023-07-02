package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.exeption_handling.NoSuchUserException;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RESTController {
    private UserService userService;

    @Autowired
    public RESTController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public ResponseEntity<List<User>> allUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/admin/{id}")
    public ResponseEntity<User> getUser(@PathVariable long id) {
        User user = userService.getUser(id);
        if (user == null) {
            throw new NoSuchUserException("There is no user with ID: " + id + " in Database");
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/admin")
    public ResponseEntity<User> addNewUser(@RequestBody User user) {
        userService.saveUser(user);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/admin/{id}")
    public ResponseEntity<User> editUser(@RequestBody User user) {
        userService.editUser(user);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable long id) {
        User user = userService.getUser(id);
        if (user == null) {
            throw new NoSuchUserException("There is no user with ID: " + id + " in Database");
        }
        userService.removeUserById(id);
        return ResponseEntity.ok("User with ID: " + id + " was deleted.");
    }

    @GetMapping("/user")
    public ResponseEntity<User> showUser(Principal principal) {
        return ResponseEntity.ok(userService.findByUserName(principal.getName()).orElse(null));
    }
}

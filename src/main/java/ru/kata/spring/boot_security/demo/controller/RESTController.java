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
    public List<User> allUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/admin/{id}")
    public User getUser(@PathVariable long id) {
        User user = userService.getUser(id);
        if (user == null) {
            throw new NoSuchUserException("There is no user with ID: " + id + " in Database");
        }
        return user;
    }

    @PostMapping("/admin")
    public User addNewUser(@RequestBody User user) {
        userService.saveUser(user);
        return user;
    }

    @PutMapping("/admin/{id}")
    public User editUser(@RequestBody User user, @PathVariable("id") Long id) {
        user.setId(id);
        userService.editUser(user);
        return user;
    }

    @DeleteMapping("/admin/{id}")
    public String deleteUser(@PathVariable long id) {
        User user = userService.getUser(id);
        if (user == null) {
            throw new NoSuchUserException("There is no user with ID: " + id + " in Database");
        }
        userService.removeUserById(id);
        return "User with ID: " + id + " was deleted.";
    }
    @GetMapping("/user")
    public User showUser(Principal principal) {
        return userService.findByUserName(principal.getName()).orElse(null);
    }
}

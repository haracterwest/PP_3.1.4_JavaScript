package ru.kata.spring.boot_security.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.Exception.UserException;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api")
public class MyRestController {

    private UserService userService;
    private RoleService roleService;

    public MyRestController(UserService userService, RoleService roleService){
        this.userService = userService;
        this.roleService = roleService;
    }
//ок
    @GetMapping("/users")
    public ResponseEntity<List<User>> showAllUsers() {
        List<User> allUsers = userService.findAll();

        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }
//ок
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user == null) {
            throw new UserException("There is no user with id = "
                    + id + " in database");
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    //ок
    @PostMapping("/users")
    public User addNewUser(@RequestBody User user){

        userService.saveUser(user);

        return user;
    }
    //ок
    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable("id") Long id, @RequestBody User user){
        User oldUser = userService.findById(id);
        userService.updateUser(user, oldUser);
        return user;
    }
    //ок
    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable Long id){
        User user = userService.findById(id);
        if(user==null){
            throw new UserException("There is no user with id = "
                    + id + " in database");
        }

        userService.deleteById(id);
        return "User with id= " + id + " was deleted";
    }

    @GetMapping("/authenticated")
    public ResponseEntity<List<User>> authenticated(Principal principal) {
        List<User> users = new ArrayList<>();
        users.add(userService.getUserByName(principal.getName()));
        return ResponseEntity.ok().body(users);
    }

}

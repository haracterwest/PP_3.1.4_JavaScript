package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {

    User findById(Long id);

    List<User> findAll();

    User saveUser(User user);

    void updateUser(User newUser, User oldUser);

    void deleteById(Long id);

    User getUserByName(String name);

    UserDetails loadUserByUsername(String username);
}

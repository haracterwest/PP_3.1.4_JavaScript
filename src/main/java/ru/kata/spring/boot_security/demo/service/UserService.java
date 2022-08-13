package ru.kata.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RolesRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

/**
 *  @Service - аннотация, объявляющая, что этот класс представляет собой сервис – компонент сервис-слоя.
 *  @Transactional - перед исполнением метода помеченного данной аннотацией начинается транзакция,
 *  после выполнения метода транзакция коммитится, при выбрасывании RuntimeException откатывается.
 *  @Autowired - отмечает конструктор, поле или метод как требующий автозаполнения инъекцией зависимости;
 */

@Service
public class UserService {      //формирование класса UserService

    private final UserRepository userRepository;
    private final RolesRepository rolesRepository;

    @Autowired      //необходимо автозаполнение инъекцией зависимости конструктора UserService
    public UserService(UserRepository userRepository,
                       RolesRepository rolesRepository) {
        this.userRepository = userRepository;
        this.rolesRepository = rolesRepository;
    }

    public User findById(Long id) {     //метод поиска по id
        return userRepository.findById(id).get();   //возврат по id
    }

    public List<User> findAll() {       //метод формирования списка всех зеров
        return userRepository.findAll();    //возврат всех юзерв из репозитормя
    }

    @Transactional
    public User saveUser(User user, String[] selecedRoles) {

        String encodedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(encodedPassword);
        Set<Role> roles = new HashSet<>();
        Arrays.stream(selecedRoles)
                .forEach(a -> roles.add(rolesRepository.findRoleByRole(a)));


        user.setRoles(roles);
        return userRepository.save(user);
    }

    @Transactional
    public User saveUser(User user) {

        String encodedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(encodedPassword);
        Object[] roles = user.getRoles().toArray();
        Object[] rolesStr = Arrays.stream(roles)
                .map(Object::toString)
                .toArray();
        Set<Role> rolesFromRepo = new HashSet<>();
        Arrays.stream(rolesStr)
                .map(a -> rolesFromRepo.add(rolesRepository.findRoleByRole(a.toString())))
                .collect(Collectors.toSet());
        user.setRoles(rolesFromRepo);
        return userRepository.save(user);
    }

    @Transactional
    public void updateUser(User newUser, User oldUser) {
        Optional<User> oUser = Optional.of(oldUser);
        String encodedPassword = new BCryptPasswordEncoder().encode(newUser.getPassword());
        oUser.get().setPassword(encodedPassword);
        oUser.get().setEmail(newUser.getEmail());
        oUser.get().setName(newUser.getName());
        oUser.get().setAge(newUser.getAge());

        Object[] roles = newUser.getRoles().toArray();
        Object[] rolesStr = Arrays.stream(roles)
                .map(Object::toString)
                .toArray();
        Set<Role> rolesFromRepo = new HashSet<>();
        Arrays.stream(rolesStr)
                .map(a -> rolesFromRepo.add(rolesRepository.findRoleByRole(a.toString())))
                .collect(Collectors.toSet());
        oUser.get().setRoles(rolesFromRepo);
        userRepository.save(oUser.get());
    }

    @Transactional
    public void updateUser(User oldU, User newU, String[] selecedRoles) {
        Optional<User> oUser = Optional.of(newU);
        String encodedPassword = new BCryptPasswordEncoder().encode(oldU.getPassword());
        oUser.get().setPassword(encodedPassword);
        oUser.get().setEmail(oldU.getEmail());
        oUser.get().setName(oldU.getName());
        oUser.get().setAge(oldU.getAge());
        Set<Role> roles = new HashSet<>();
        Arrays.stream(selecedRoles)
                .forEach(a -> roles.add(rolesRepository.findRoleByRole(a)));


        oUser.get().setRoles(roles);
        userRepository.save(oUser.get());
    }

    @Transactional
    public void deleteById(Long id) {       //удаление юзера по id
        userRepository.deleteById(id);
    }

    public User getUserByName(String name) {    //поиск по имени
        return userRepository.getUserByName(name);
    }
}

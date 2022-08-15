package ru.kata.spring.boot_security.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.exception.UserException;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RolesRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Service - аннотация, объявляющая, что этот класс представляет собой сервис – компонент сервис-слоя.
 * @Transactional - перед исполнением метода помеченного данной аннотацией начинается транзакция,
 * после выполнения метода транзакция коммитится, при выбрасывании RuntimeException откатывается.
 * @Autowired - отмечает конструктор, поле или метод как требующий автозаполнения инъекцией зависимости;
 */

@Service
public class UserServiceImpl implements UserService {      //формирование класса UserService

    private final UserRepository userRepository;
    private final RolesRepository rolesRepository;

    @Autowired      //необходимо автозаполнение инъекцией зависимости конструктора UserService
    public UserServiceImpl(UserRepository userRepository,
                           RolesRepository rolesRepository) {
        this.userRepository = userRepository;
        this.rolesRepository = rolesRepository;
    }

    @Override
    public User findById(Long id) {     //метод поиска по id
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserException("There is no user with id = "
                    + id + " in database");
        }//возврат по id
        return user.get();
    }

    @Override
    public List<User> findAll() {       //метод формирования списка всех зеров
        return userRepository.findAll();    //возврат всех юзерв из репозитормя
    }

    @Override
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

    @Override
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

    @Override
    @Transactional
    public void deleteById(Long id) {       //удаление юзера по id
        userRepository.deleteById(id);
    }

    @Override
    public User getUserByName(String name) {    //поиск по имени
        return userRepository.getUserByName(name);
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {   //метод для обработки исключения
        User user = getUserByName(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("пользователь с именем '%s' не найден ", username));
        }
        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), user.getAuthorities());
    }
}

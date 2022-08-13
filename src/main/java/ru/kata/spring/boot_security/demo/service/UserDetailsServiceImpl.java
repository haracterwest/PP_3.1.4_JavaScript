package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;

/**
 *  Service - аннотация, объявляющая, что этот класс представляет собой сервис – компонент сервис-слоя.
 *  Transactional - перед исполнением метода помеченного данной аннотацией начинается транзакция,
 *  после выполнения метода транзакция коммитится, при выбрасывании RuntimeException откатывается.
 *  Autowired - отмечает конструктор, поле или метод как требующий автозаполнения инъекцией зависимости;
 *  Override - перед объявлением метода означает, что метод переопределяет объявление метода в базовом классе;
 */

@Service

//создание класса с реализацией интерфейса
    public class UserDetailsServiceImpl implements UserDetailsService {

    //конструктор вместо:

    //@Autowired
    //private UserService userService;

    private final UserService userService;

    @Autowired
    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }




    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {   //метод для обработки исключения
        User user = userService.getUserByName(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("пользователь с именем '%s' не найден ", username));
        }
        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), user.getAuthorities());
    }
}

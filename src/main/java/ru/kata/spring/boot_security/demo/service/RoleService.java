package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repository.RolesRepository;

import java.util.List;


/**
 *  Service - аннотация, объявляющая, что этот класс представляет собой сервис – компонент сервис-слоя.
 */

@Service

//класс, формирующий роли
public class RoleService {

     private final RolesRepository rolesRepository;

    @Autowired
    public RoleService(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    @Transactional
    public List <Role> getRoles() {
        return rolesRepository.findAll();                                    //возврат списка
    }
}

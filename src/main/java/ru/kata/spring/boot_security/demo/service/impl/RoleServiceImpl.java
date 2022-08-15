package ru.kata.spring.boot_security.demo.service.impl;

import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repository.RolesRepository;
import ru.kata.spring.boot_security.demo.service.RoleService;

import java.util.List;


/**
 * Service - аннотация, объявляющая, что этот класс представляет собой сервис – компонент сервис-слоя.
 */

//класс, формирующий роли
@Service
public class RoleServiceImpl implements RoleService {

    private final RolesRepository rolesRepository;

    public RoleServiceImpl(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    @Override
    public List<Role> getRoles() {
        return rolesRepository.findAll();                                    //возврат списка
    }
}

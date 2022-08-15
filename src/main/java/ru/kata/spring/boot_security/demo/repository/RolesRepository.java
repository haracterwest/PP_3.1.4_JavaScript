package ru.kata.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kata.spring.boot_security.demo.model.Role;


/**
 * @Query - аннотация означает, что в случае вызова текущего метода будет выполнен запрос к базе данных, запрос указан в параметрах;
 */

public interface RolesRepository extends JpaRepository<Role, Long> {
    //формирование интерфейса с наследованием от JpaRepository

    Role findRoleByRole(String role);
}

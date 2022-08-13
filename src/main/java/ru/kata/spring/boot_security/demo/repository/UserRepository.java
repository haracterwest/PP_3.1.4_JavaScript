package ru.kata.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.kata.spring.boot_security.demo.model.User;


/**
 * @Query - аннотация означает, что в случае вызова текущего метода будет выполнен запрос к базе данных, запрос указан в параметрах;
 */

public interface UserRepository extends JpaRepository<User, Long> {     //формирование интерфейса с наследованием от JpaRepository

    @Query("SELECT c FROM User c WHERE c.name = :name")
    User getUserByName(@Param("name") String name);     //указание по какому параметру искать в БД
}

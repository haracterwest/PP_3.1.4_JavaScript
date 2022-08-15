package ru.kata.spring.boot_security.demo.model;

import org.springframework.security.core.GrantedAuthority;
import javax.persistence.*;
import java.util.Objects;


/**
 * @Entity - аннотация, связывающая сущность Entity (POJO-класс) с БД, указывается над классом;
 * @Table - указывает на имя таблицы, которая будет отображаться в этой сущности, указывается над классом;
 * @Id - аннотация, оопределяющая primary key в entity bean;
 * @Column - аннотация, которая используется для определения соответствия между атрибутами в классе сущности и полями в таблице данных;
 * @GeneratedValue - задает стратегию создания основных ключей;
 * @Override - перед объявлением метода означает, что метод переопределяет объявление метода в базовом классе;
 * @Transient - указывает, что свойство не нужно записывать. Значения под этой аннотацией не записываются
 * в базу данных (также не участвуют в сериализации). static и final переменные экземпляра всегда transient.
 * @ManyToMany - определяет атрибут-коллекцию ссылок на сущность с типом ассоциации много-ко-многим
 * (Ассоциация много-ко-многим всегда имеет ведущую сторону и может иметь обратную сторону - ведомую.
 * На ведущей стороне указывается дополнительная аннотация @JoinTable, на ведомой стороне - параметр mappedBy).
 */

@Entity
@Table(name = "roles")                                      //создание таблицы
public class Role implements GrantedAuthority {             //класс Role реализует интерфейс GrantedAuthority

    @Id
    @Column(name = "role_id")                               //определение соответствия в между name и "role_id"
    @GeneratedValue(strategy = GenerationType.IDENTITY)     //генерация первичнго ключа, используя стратегию .IDENTITY
    private Long id;

    private String role;


    public Role(Long id) {
        this.id = id;
    }

    public Role(String role) {
        this.role = role;
    }


    public Role() {     //пустой конструктор - условие Entity
    }

    public Long getId() {
        return id;
    }


    @Override
    public String toString() {                          //замена элемента типа String
        role
                .replace("[", "")
                .replace("]", "");
        return role;                                    //возвращает role с учётом замены

    }

    public String getRole() {                           //замена элемента типа String
        return role.replace("[", "")
                .replace("]", "");

    }

    @Override                                       //переопределение интерфейса с возвращением role
    public String getAuthority() {
        return role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role1 = (Role) o;
        return Objects.equals(getId(), role1.getId()) && Objects.equals(getRole(), role1.getRole());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getRole());
    }
}

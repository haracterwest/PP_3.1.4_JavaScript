package ru.kata.spring.boot_security.demo.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


/** @Data - на этапе компиляции сгенерирует геттеры\сеттеры для всех полей, toString и переопределит equals и hashCode;
 *  @Table - указывает на имя таблицы, которая будет отображаться в этой сущности, указывается над классом;
 *  @Id - аннотация, оопределяющая primary key в entity bean;
 *  @Column - аннотация, которая используется для определения соответствия между атрибутами в классе сущности и полями в таблице данных;
 *  @GeneratedValue - задает стратегию создания основных ключей;
 *  @ManyToMany - определяет атрибут-коллекцию ссылок на сущность с типом ассоциации много-ко-многим
 *  (Ассоциация много-ко-многим всегда имеет ведущую сторону и может иметь обратную сторону - ведомую.
 *  На ведущей стороне указывается дополнительная аннотация @JoinTable, на ведомой стороне - параметр mappedBy).
 *  @JoinTable - указывает на связь с таблицей;
 *  @JoinColumn - применяется, когда внешний ключ находится в одной из сущностей. Может применяться с обеих сторон взаимосвязи;
 *  @Override - перед объявлением метода означает, что метод переопределяет объявление метода в базовом классе;
 */


@Data
@Entity
@Table(name = "users")                          //создание таблицы
public class User implements UserDetails {      //класс User реализует интерфейс UserDetails

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)     //генерация первичнго ключа, используя стратегию .IDENTITY
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "age")
    private Integer age;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"))
    private Set<Role> roles;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getRole().
                        replace("[", "").replace("]", "")))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public String getRolesToString() {
        return roles.toString().replace("[", "")
                .replace("]", "");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(getId(), user.getId()) && Objects.equals(getName(), user.getName()) && Objects.equals(getEmail(), user.getEmail()) && Objects.equals(getPassword(), user.getPassword()) && Objects.equals(getRoles(), user.getRoles());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getEmail(), getPassword(), getRoles());
    }
}

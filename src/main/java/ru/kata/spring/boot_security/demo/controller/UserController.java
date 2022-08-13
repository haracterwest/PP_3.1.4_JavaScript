package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

/**
 * @Controller - аннотация для маркировки java класса, как класса контроллера;
 * @RequestMapping - используется для маппинга урл-адреса запроса на указанный метод или класс;
 * @GetMapping - используется для обработки get-запроса;
 * @Autowired - отмечает конструктор, поле или метод как требующий автозаполнения инъекцией зависимости;
 */

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    //метод контроллера, который складывает значения в модель и возвращает шаблон "user"
    @GetMapping()
    public String getUserPage(Model model, Principal principal) {       //текущий юзер, содержится в объекте principal
        User user = userService.getUserByName(principal.getName());     //возвращает юзера по имени
        model.addAttribute("user", user);                   //возвращает в модель user по ключу "user"
        return "user";                                                  //возвращает шаблон "user"
    }
}

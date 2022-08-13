package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;

/**
 * @Controller - аннотация для маркировки java класса, как класса контроллера;
 * @RequestMapping - используется для маппинга урл-адреса запроса на указанный метод или класс;
 * @GetMapping - используется для обработки get-запроса;
 * @PostMapping - используется для обработки post-запроса;
 * @PatchMapping - используется для обновления ресурсов;
 * @DeleteMapping - Обрабатывает delete-запросы
 * @ModelAttribute – это аннотация, которая связывает параметр метода или
 * возвращаемое значение метода с именованным атрибутом модели, а затем предоставляет его веб-представлению.
 * @PathVariable - используется для обработки переменных шаблона в
 * отображении URI запроса и использовать их в качестве параметров метода.
 *
 */

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;


    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    //метод контроллера, который складывает значения в модель и возвращает шаблон "admin"
    @GetMapping()
    public String getAllUsers(Model model, Principal principal) {
        List<User> users = userService.findAll();
        User newU = new User();
        User user = userService.getUserByName(principal.getName());//возвращает весь список users
        model.addAttribute("users", users);
        model.addAttribute("user", newU);
        model.addAttribute("currentUser", user);
        model.addAttribute("allroles", roleService.getRoles());

        return "admin";                                     // возвращает шаблон "admin"
    }
//
//    //метод контроллера, который складывает значения в модель по Get-запросу "new" и возвращает шаблон "admin/user-create"
//    @GetMapping("/new")
//    public String addUser(Model model) {
//        model.addAttribute("user", new User());                     //добавляет нового user по ключу "user"
//        model.addAttribute("allroles", roleService.getRoles());     //возвращает роль из списка по ключу "allroles"
//        return "admin";                                             //возвращает шаблон "admin/user-create"
//    }
//
//    //метод котнтроллера, который по Post-запросу сохранает юзера, пришедшего с представления
//    @PostMapping()
//    public String create(@ModelAttribute("user") User user, @RequestParam("rolesList") String[] selectedRoles) {
//        userService.saveUser(user, selectedRoles);
//        return "redirect:/admin";
//    }
//    //метод контроллера, который по Get-запросу выдает юзера с определённым id
//    @GetMapping(value = "/{id}/edit")                                   //в запросе указываем значение {id}, которое связано с параметром метода ("id")
//    public String edit(Model model, @PathVariable("id") Long id) {
//        User user = userService.findById(id);//по значению "id" из url кладем в переменную Long id
//        model.addAttribute("user", user);
//        model.addAttribute("userRoles", roleService.getRoles());//ищем юзера по "id", кладём в модель
//        return "admin/user-edit";                                           // возвращает шаблон "admin/user-edit"
//    }
//
//    //метод контроллера, который обновляет данные модели юзера
//    @PatchMapping("/{id}")
//    public String update(@ModelAttribute("user") User user, @PathVariable("id") Long id, @RequestParam("rolesListedit") String[] selectedRoles) {
//        User newU = userService.findById(id);
//
//        userService.updateUser(user, newU, selectedRoles);
//
//        return "redirect:/admin";
//    }
//
//    //метод удаляет по id
//    @DeleteMapping("/{id}")                               //в запросе указываем значение {id}, которое связано с параметром метода ("id")
//    public String delete(@PathVariable("id") Long id) {     //по значению "id" из url кладем в переменную Long id
//        userService.deleteById(id);                         //удаляет по id
//        return "redirect:/admin";                           //направляет на юрл /admin
//    }
//
//    //метод, который ищет юзера по имени
//    @GetMapping("/by_user_name")        //
//    public String findByName(@PathVariable("name") String name) {   //по значению "name" из url кладем в переменную String name
//        User user = userService.getUserByName(name);                //возвращает name, кладём в переменную user
//
//        return user.getUsername();                                  //возвращает user
//    }
}

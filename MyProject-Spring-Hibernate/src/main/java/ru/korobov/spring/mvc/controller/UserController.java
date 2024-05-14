package ru.korobov.spring.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.korobov.spring.mvc.model.User;
import ru.korobov.spring.mvc.service.UserService;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/")
    public String allUsers(ModelMap model, @ModelAttribute("user") User user) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "web";
    }

    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam Long id) {
        userService.deleteUser(id);
        return "redirect:/";
    }

    @GetMapping("/edit")
    public String editUser(Long id, ModelMap model) {
        model.addAttribute("user", userService.getUserById(id));
        return "web";
    }

    @PostMapping("/addUser")
    public String addUser(@ModelAttribute User user) {
        System.out.println("ПРИВЕТ");
        userService.saveUser(user);
        return "redirect:/";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute User user, @RequestParam long id) {
        user.setId(id);
        userService.updateUser(user);
        return "edit_user";
    }
}

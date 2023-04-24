package kg.mara.babyfood.controller.rest;


import kg.mara.babyfood.dao.UserDao;
import kg.mara.babyfood.entities.User;
import kg.mara.babyfood.enums.Role;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@Controller
@RequestMapping("/user")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {

    private final UserDao userRepo;

    public UserController(UserDao userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userRepo.findAll());

        return "userList";
    }

    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());

        return "userEdit";
    }

    @PostMapping
    public String userSave(@RequestParam Set<Role> roles, @RequestParam("userId") User user) {
        user.getRoles().clear();
        user.setRoles(roles);
        userRepo.save(user);

        return "redirect:/user";
    }
    @PostMapping("/delete")
    public String userDelete(@RequestParam("userId") User user) {
        userRepo.delete(user);
        return "redirect:/user";
    }
}

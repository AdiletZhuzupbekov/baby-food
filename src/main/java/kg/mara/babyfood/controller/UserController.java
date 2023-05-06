package kg.mara.babyfood.controller;


import kg.mara.babyfood.dao.OrderDao;
import kg.mara.babyfood.dao.UserDao;
import kg.mara.babyfood.entities.User;
import kg.mara.babyfood.enums.Role;
import kg.mara.babyfood.service.OrderService;
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
    private final OrderDao orderDao;
    private final OrderService orderService;

    public UserController(UserDao userRepo, OrderDao orderDao, OrderService orderService) {
        this.userRepo = userRepo;
        this.orderDao = orderDao;
        this.orderService = orderService;
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
    @GetMapping("/orders")
    public String getOrders(Model model){
        model.addAttribute("orders",orderService.getOrders());
        return "order";
    }
}

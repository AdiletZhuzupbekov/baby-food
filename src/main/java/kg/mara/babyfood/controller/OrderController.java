package kg.mara.babyfood.controller;

import kg.mara.babyfood.entities.OrderEntity;
import kg.mara.babyfood.entities.User;
import kg.mara.babyfood.enums.OrderType;
import kg.mara.babyfood.service.OrderService;
import kg.mara.babyfood.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    @GetMapping("/product/order")
    public String getOrders(Model model){
        List<OrderEntity> orders = orderService.getOrders();
        List<User> drivers = userService.getDrivers();
        model.addAttribute("orders", orders);
        model.addAttribute("drivers", drivers);
        return "order";
    }
    @PostMapping("/chooseDriver")
    public String setDriver(@RequestParam Long order, @RequestParam String driver){
        OrderEntity orderEntity = orderService.getOrder(order);
        orderEntity.setDriver(driver);
        orderEntity.setOrderType(OrderType.ДОСТАВКА);
        orderService.saveOrder(orderEntity);
        return "redirect:/product/order";

    }
}

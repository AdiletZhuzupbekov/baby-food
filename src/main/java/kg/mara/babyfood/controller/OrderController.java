package kg.mara.babyfood.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderController {

    @GetMapping("/product/order")
    public String getOrders(){
        return "order";
    }
}

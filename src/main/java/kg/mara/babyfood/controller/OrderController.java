package kg.mara.babyfood.controller;

import kg.mara.babyfood.entities.OrderEntity;
import kg.mara.babyfood.entities.User;
import kg.mara.babyfood.enums.OrderType;
import kg.mara.babyfood.service.ExcelService;
import kg.mara.babyfood.service.OrderService;
import kg.mara.babyfood.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;
    private final ExcelService excelService;

    @GetMapping("/product/order")
    public String getOrders(Model model){
        List<OrderEntity> orders = orderService.getOrders();
        List<User> drivers = userService.getDrivers();
        List<OrderEntity> orderInProcess = orderService.getOrdersInProcess();
        List<OrderEntity> closed = orderService.getOrdersClosed();
        model.addAttribute("orders", orders);
        model.addAttribute("drivers", drivers);
        model.addAttribute("delivering",orderInProcess);
        model.addAttribute("closed", closed);
        return "order";
    }
    @PostMapping("/chooseDriver")
    public String setDriver(@RequestParam Long orderId, @RequestParam String driver) {
        OrderEntity orderEntity = orderService.getOrder(orderId);
        orderEntity.setDriver(driver);
        orderEntity.setOrderType(OrderType.ДОСТАВКА);
        orderService.saveOrder(orderEntity);
        return "redirect:/product/order";
    }
    @PostMapping("/cancel-order")
    public String deleteOrder(@RequestParam Long orderId){
        orderService.cancelOrder(orderId);
        return "redirect:/product/order";
    }
    @PostMapping("/close-order")
    public String closeOrder(
            @RequestParam("counts") List<Integer> counts,
            @RequestParam("barcode") List<String> barcode,
            @RequestParam Long orderId
            ){
        orderService.closeOrder(orderId, barcode, counts);
        return "redirect:/product/order";
    }

    @GetMapping("/export/{orderId}")
    public void export(@PathVariable Long orderId, HttpServletResponse response) throws IOException {
        OrderEntity orderEntity = orderService.getOrder(orderId);
        excelService.exportToExcel(orderEntity, response);
    }
}

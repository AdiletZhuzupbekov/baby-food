package kg.mara.babyfood.controller;

import kg.mara.babyfood.service.OrderService;
import kg.mara.babyfood.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequiredArgsConstructor
public class ReportController {
    private final ProductService productService;
    private final OrderService orderService;

    @GetMapping("/report")
    public String getReport(@RequestParam(name = "start", required = false) String start,
                            @RequestParam(name = "end", required = false) String end,
                            Model model){
        String dateTimeString = "2023-05-01T01:00";
        LocalDateTime startDt;
        LocalDateTime endDt;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        if (start != null){
            startDt = LocalDateTime.parse(start, formatter);
        }else {
            startDt = LocalDateTime.parse(dateTimeString, formatter);
        }
        if (end != null){
            endDt = LocalDateTime.parse(end, formatter);
        }else {
            endDt = LocalDateTime.now();
        }
        Double base = productService.getBaseTotal();
        Double sell = productService.getSellTotal();
        Double bydt = orderService.getByDt(startDt, endDt);
        model.addAttribute("base", base);
        model.addAttribute("sell", sell);
        model.addAttribute("bydt",bydt);

        return "report";
    }
}

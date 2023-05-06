package kg.mara.babyfood.controller.rest;

import kg.mara.babyfood.entities.ProductEntity;
import kg.mara.babyfood.model.Product;
import kg.mara.babyfood.service.OrderService;
import kg.mara.babyfood.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
@CrossOrigin
@RequiredArgsConstructor
public class MainRestController {


    private final ProductService productService;
    private final OrderService orderService;

    @GetMapping()
    public Page getProductList(
            @RequestParam(required = false) Integer _limit,
            @RequestParam (required = false) Integer _page,
            @RequestParam(required = false) String category) {
        PageRequest pr;
        Page<ProductEntity> productEntities;
        if (_limit != null && _page != null) {
            pr = PageRequest.of(_page, _limit);
        } else {
            pr = PageRequest.of(0, 15);
        }
        productEntities = productService.getProducts(pr, category);
        return productEntities;
    }
    @PostMapping("/create-order")
    public String createOrder(@RequestBody Map<String, Object> payload){

        List<Map<String, Object>> products = (List<Map<String, Object>>) payload.get("product");
        List<Product> productList = new ArrayList<>();
        Double price = (Double) payload.get("price");
        String orderId = payload.get("order").toString();
        String address = payload.get("address").toString();
        for (Map<String,Object> o : products){
            Product p = new Product();
            p.setBarCode(o.get("barcode").toString());
            p.setCount((Integer) o.get("count"));
            productList.add(p);
        }
        orderService.createOrder(productList, price, orderId, address);
        return "SUCCESS";
    }



}

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
import java.util.Optional;


@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class MainRestController {


    private final ProductService productService;
    private final OrderService orderService;
    @GetMapping("/{id}")
    public Optional<ProductEntity> getProduct(@PathVariable(required = false, name = "id") Long id){
        return productService.getProduct(id);
    }
    @GetMapping()
    public Page getProductList(
            @RequestParam(required = false) Integer _limit,
            @RequestParam (required = false) Integer _page,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String q,
            @RequestParam(required = false) List<String> name,
            @RequestParam(required = false) List<String> size,
            @RequestParam(required = false) List<String> age) {
        PageRequest pr;
        Page<ProductEntity> productEntities;

        if (_limit != null && _page != null) {
            if (_page > 0) {
                _page -= 1;
            }
            pr = PageRequest.of(_page, _limit);
        } else {
            pr = PageRequest.of(0, 15);
        }
        productEntities = productService.getProducts(pr, category, q, name, size, age);
        return productEntities;
    }
    @PostMapping("/create-order")
    public String createOrder(@RequestBody Map<String, Object> payload){

        List<Map<String, Object>> products = (List<Map<String, Object>>) payload.get("products");
        List<Product> productList = new ArrayList<>();
        Integer price = (Integer) payload.get("total");
        String name =  payload.get("name").toString();
        String address = payload.get("address").toString();
        String phone = payload.get("phone").toString();
        String orderId = payload.get("id").toString();
        for (Map<String,Object> o : products){
            Product p = new Product();
            p.setBarCode(o.get("barcode").toString());
            p.setCount((Integer) o.get("count"));
            productList.add(p);
        }
        orderService.createOrder(productList, price, orderId, name, address, phone);
        return "SUCCESS";
    }



}

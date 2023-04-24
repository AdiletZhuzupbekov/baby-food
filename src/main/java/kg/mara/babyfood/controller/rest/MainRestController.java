package kg.mara.babyfood.controller.rest;

import kg.mara.babyfood.entities.ProductEntity;
import kg.mara.babyfood.model.Product;
import kg.mara.babyfood.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class MainRestController {


    private  final ProductService productService;

    @GetMapping("/products")
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



}

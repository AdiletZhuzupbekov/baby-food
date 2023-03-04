package kg.mara.babyfood.controller.rest;

import kg.mara.babyfood.entities.ProductEntity;
import kg.mara.babyfood.service.ProductService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class MainRestController {


    private  final ProductService productService;

    @GetMapping("/product")
    public Page<ProductEntity> getProductList(@RequestParam(required = false) String type,
                                              @RequestParam(required = false) String q,
                                              @RequestParam(required = false) int _limit,
                                              @RequestParam(required = false) int _page) {
        return productService.getProducts(type, q, PageRequest.of(_page, _limit));
    }


}

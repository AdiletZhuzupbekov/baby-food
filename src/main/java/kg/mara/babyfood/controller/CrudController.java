package kg.mara.babyfood.controller;

import kg.mara.babyfood.entities.ProductEntity;
import kg.mara.babyfood.model.Product;
import kg.mara.babyfood.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CrudController {
    private final ProductService productService;

    public CrudController(ProductService productService) {
        this.productService = productService;
    }
    @GetMapping("/")
    public String mainPage(Model model){
        PageRequest pr = PageRequest.of(0,10);
        Page<ProductEntity> products = productService.getProducts(null, null, pr);
        model.addAttribute("product", products);
        model.addAttribute("pageName", "Baby Food");
        return "product";
    }
    @GetMapping("/product-crud")
    public String getProductCrud(Model model){
        model.addAttribute("pageName", "Добавить продукт");
        return "product-crud";
    }

    @PostMapping("/add-product")
    public String addProduct(@RequestParam String barCode,
                             @RequestParam String name,
                             @RequestParam String nameRus,
                             @RequestParam String description,
                             @RequestParam String size,
                             @RequestParam Double price,
                             @RequestParam String type,
                             @RequestParam String image
                             ){
        Product product = new Product();
        product.setType(type);
        product.setPrice(price);
        product.setSize(size);
        product.setDescription(description);
        product.setName(name);
        product.setNameRus(nameRus);
        product.setImage(image);
        product.setBarCode(barCode);
        productService.saveProducts(product);
        return "redirect:/product-crud";
    }
}

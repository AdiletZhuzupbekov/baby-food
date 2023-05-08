package kg.mara.babyfood.controller;

import kg.mara.babyfood.entities.ProductEntity;
import kg.mara.babyfood.model.Product;
import kg.mara.babyfood.service.ExcelService;
import kg.mara.babyfood.service.FileUploadUtil;
import kg.mara.babyfood.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final ProductService productService;
    private final ExcelService excelService;

    @GetMapping("/")
    public String mainPage(Model model){
        List<ProductEntity> products = productService.getProductsForPanel();
        model.addAttribute("product", products);
        model.addAttribute("pageName", "Baby Food");
        return "product";
    }
    @GetMapping("/product-crud")
    public String getProductCrud(Model model){
        model.addAttribute("pageName", "Добавить продукт");
        return "product-crud";
    }
    @GetMapping("/filter")
    public String getByFilter(Model model, @RequestParam String filter){
        Product product = productService.findByFilter(filter);
        if (product != null){
            model.addAttribute("product", product);
            return "product-crud";
        }else {
            model.addAttribute("code", filter);
            return "product-crud";
        }
    }

    @PostMapping("/product/add")
    public String addProduct(@RequestParam(required = false) Long id,
                             @RequestParam String barCode,
                             @RequestParam String name,
                             @RequestParam(required = false) String nameRus,
                             @RequestParam(required = false) String description,
                             @RequestParam(required = false) String size,
                             @RequestParam(required = false) String age,
                             @RequestParam(required = false) Double originPrice,
                             @RequestParam(required = false) Double price,
                             @RequestParam(required = false) String category,
                             @RequestParam(required = false)Integer count,
                             @RequestParam(required = false) Integer plus,
                             @RequestParam(value = "image", required = false)MultipartFile multipartFile
                             ) throws IOException {
        String fileName = null;
        if (multipartFile != null) {
             fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        }
        Product product = new Product();
        if (id != null){
            product.setId(id);
        }
        if (!age.equals(""))
        product.setAge(age);
        product.setOriginalPrice(originPrice);
        if (!category.equals(""))
        product.setCategory(category);
        product.setPrice(price);
        if (!size.equals(""))
        product.setSize(size);
        product.setDescription(description);
        product.setName(name);
        product.setNameRus(nameRus);
        if (fileName != null) {
            product.setImage(fileName);
        }
        product.setBarCode(barCode);
        if (plus != null){
            product.setCount(count+plus);
        }else {
            product.setCount(count);
        }
        productService.saveProducts(product);
        String uploadDir = "product-photos";
        FileUploadUtil.saveFile(uploadDir,fileName,multipartFile);
        return "redirect:/product-crud";
    }
    @GetMapping("/revision")
    public void export(HttpServletResponse response) throws IOException {
        excelService.revisionToExcel(response);
    }
}

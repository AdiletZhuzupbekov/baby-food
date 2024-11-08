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
    private String nameToFind = "";

    @GetMapping("/")
    public String mainPage(Model model){
        List<ProductEntity> products = productService.getProductsForPanel();

        model.addAttribute("product", products);
        model.addAttribute("pageName", "Baby Food");
        return "product";
    }
    @GetMapping("/main-filter")
    public String mainFilter(@RequestParam(required = false) String filter, Model model){
        List<ProductEntity> products;
         if (filter == null){
              if (nameToFind.isEmpty()) {
               products = productService.getProductsForPanel();
           }else {
               products = productService.getProductByFilter(nameToFind);
               filter = nameToFind;
           }
        } else {
             if (filter.isEmpty()){
                 products = productService.getProductsForPanel();
                 nameToFind = "";
             }else {
                 nameToFind = filter;
                 products = productService.getProductByFilter(filter);
             }
        }
        model.addAttribute("toFind" , filter);
        model.addAttribute("product", products);
        model.addAttribute("pageName", "Baby Food");
        return "product";
    }
    @PostMapping("/save-product-changes")
    public String saveChanges(
            @RequestParam(required = false) String filter,
            @RequestParam double originalPrice,
            @RequestParam double price,
            @RequestParam Long id,
            @RequestParam int count,
            @RequestParam String criteria,
            @RequestParam String category,
            @RequestParam(value = "image", required = false)MultipartFile multipartFile
    ) throws IOException{
        String fileName = null;
        if (multipartFile != null) {
            fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        }
        if (fileName != null) {
            String uploadDir = "product-photos";
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        }
        nameToFind = filter;
        productService.saveChanges(id, originalPrice, price, count, criteria, fileName, category);
        return "redirect:/main-filter";
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
                             @RequestParam(required = false) String criteria,
                             @RequestParam(value = "image", required = false)MultipartFile multipartFile
                             ) throws IOException {
            String fileName = null;
            if (multipartFile != null) {
                fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            }
            if (!fileName.isEmpty()) {
                String uploadDir = "product-photos";
                FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
            }
            Product product = new Product();
            if (id != null) {
                product.setId(id);
            }
            if (age != null)
                product.setAge(age);
            product.setOriginalPrice(originPrice);
            if (category != null)
                product.setCategory(category);
            product.setPrice(price);
            if (size != null)
                product.setSize(size);
            product.setDescription(description);
            product.setName(name);
            product.setNameRus(nameRus);
            if (!fileName.isEmpty()) {
                product.setImage(fileName);
            }
            product.setBarCode(barCode);
            if (count == null){
                count = 0;
            }
            if (plus != null) {
                product.setCount(count + plus);
            } else {
                product.setCount(count);
            }
            if (criteria != null){
                product.setCriteria(criteria);
            }
        productService.saveProducts(product);
        return "redirect:/product-crud";
    }
    @GetMapping("/revision")
    public void export(HttpServletResponse response) throws IOException {
        excelService.revisionToExcel(response);
    }
    @PostMapping("/delete-product")
    public String delete(@RequestParam Long productId, Model model){
        List<ProductEntity> products = productService.getProductsForPanel();
        model.addAttribute("product", products);
        model.addAttribute("pageName", "Baby Food");
        productService.deleteRequest(productId);
        return "redirect:/";
    }
    @PostMapping("/accept-delete")
    public String accept(@RequestParam Long productId, Model model){
        productService.deleteProduct(productId);
        List<ProductEntity> products = productService.getProductsForPanel();
        model.addAttribute("product", products);
        model.addAttribute("pageName", "Baby Food");
        return "redirect:/delete-request";
    }
    @GetMapping("/delete-request")
    public String deleteRequest(Model model){
        List<ProductEntity> products = productService.getDeleteRequestedProducts();

        model.addAttribute("product", products);
        model.addAttribute("pageName", "Baby Food");
        return "delete-order";
    }
}

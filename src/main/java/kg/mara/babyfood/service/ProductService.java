package kg.mara.babyfood.service;

import kg.mara.babyfood.entities.ProductEntity;
import kg.mara.babyfood.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface ProductService {
    Page<ProductEntity> getProducts(String type, String q, PageRequest pr);

    void saveProducts(Product products);
}

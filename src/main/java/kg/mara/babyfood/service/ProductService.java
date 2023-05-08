package kg.mara.babyfood.service;

import kg.mara.babyfood.entities.ProductEntity;
import kg.mara.babyfood.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    Page<ProductEntity> getProducts(Pageable paging, String category);

    ProductEntity saveProducts(Product products);

    Product findByFilter(String filter);

    List<ProductEntity> getProductsForPanel();

    List<ProductEntity> findAll();
}

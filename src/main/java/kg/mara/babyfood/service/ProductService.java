package kg.mara.babyfood.service;

import kg.mara.babyfood.entities.ProductEntity;
import kg.mara.babyfood.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    Page<ProductEntity> getProducts(
            Pageable paging, String category, String q, List<String> name, List<String> size, List<String> age);

    ProductEntity saveProducts(Product products);

    Product findByFilter(String filter);

    List<ProductEntity> getProductsForPanel();

    List<ProductEntity> findAll();

    Double getBaseTotal();

    Double getSellTotal();

    void deleteProduct(Long productId);

    Optional<ProductEntity> getProduct(Long id);

    void saveChanges(Long id, double originalPrice, double price, int count);

    List<ProductEntity> getProductByFilter(String filter);
}

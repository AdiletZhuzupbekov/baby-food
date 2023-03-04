package kg.mara.babyfood.mapper;

import kg.mara.babyfood.entities.ProductEntity;
import kg.mara.babyfood.model.Product;

public interface ProductMapper {

        Product fromEntity(ProductEntity pe);
        ProductEntity toEntity(Product product);
}

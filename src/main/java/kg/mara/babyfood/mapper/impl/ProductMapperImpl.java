package kg.mara.babyfood.mapper.impl;

import kg.mara.babyfood.entities.ProductEntity;
import kg.mara.babyfood.mapper.ProductMapper;
import kg.mara.babyfood.model.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductMapperImpl implements ProductMapper {
    @Override
    public Product fromEntity(ProductEntity pe) {
        Product product = new Product();
        product.setId(pe.getId());
        product.setName(pe.getName());
        product.setNameRus(pe.getNameRus());
        product.setDescription(pe.getDescription());
        product.setPrice(pe.getPrice());
        product.setSize(pe.getSize());
        product.setType(pe.getType());
        return product;
    }

    @Override
    public ProductEntity toEntity(Product pe) {
        ProductEntity product = new ProductEntity();
        product.setId(pe.getId());
        product.setName(pe.getName());
        product.setNameRus(pe.getNameRus());
        product.setDescription(pe.getDescription());
        product.setPrice(pe.getPrice());
        product.setSize(pe.getSize());
        product.setType(pe.getType());
        return product;
    }
}

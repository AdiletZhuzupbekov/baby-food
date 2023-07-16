package kg.mara.babyfood.service.impl;

import kg.mara.babyfood.dao.ProductDao;
import kg.mara.babyfood.entities.ProductEntity;
import kg.mara.babyfood.mapper.ProductMapper;
import kg.mara.babyfood.model.Product;
import kg.mara.babyfood.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductDao productDao;
    private final ProductMapper productMapper;

    @Override
    public Page<ProductEntity> getProducts(
            Pageable paging, String category, String q, List<String> name, List<String> size, List<String> age) {
        if (category != null && q != null){
            return productDao.findAllByCountGreaterThanAndCategoryIsLikeAndNameContainingIgnoreCaseOrNameRusContainingIgnoreCase(0,category,q, q,paging);
        }else  if (category != null){
            if (name != null && size != null){
                return productDao.findAllByCountGreaterThanAndNameInAndSizeInAndCategoryIsLike(0,name, size, category, paging);
            }else if (name != null && age != null){
                return productDao.findAllByCountGreaterThanAndNameInAndAgeInAndCategoryIsLike(0, name, age, category, paging);
            }else if(size != null){
                return productDao.findAllByCountGreaterThanAndSizeInAndCategoryIsLike(0,size, category, paging);
            }else if(name != null){
                return productDao.findAllByCountGreaterThanAndNameInAndCategoryIsLike(0,name, category,paging);
            }else if(age != null){
                return productDao.findAllByCountGreaterThanAndAgeInAndCategoryIsLike(0,age, category,paging);
            }else {
                return productDao.findAllByCountGreaterThanAndCategoryIsLikeIgnoreCaseOrderByIdAsc(0,paging, category);
            }
        } else if (q != null){
            return productDao.findAllByCountGreaterThanAndNameContainingIgnoreCaseOrNameRusContainingIgnoreCase(0,q, q,paging);
        }else {
            return productDao.findAllByCountGreaterThan(0,paging);
        }
    }


    @Override
    public ProductEntity saveProducts(Product product) {
         ProductEntity pe = productDao.findByBarCode(product.getBarCode());
         if (pe != null) {
             pe.setName(product.getName());
             pe.setNameRus(product.getNameRus());
             pe.setDescription(product.getDescription());
             pe.setCount(product.getCount());
             pe.setPrice(product.getPrice());
             pe.setOriginalPrice(product.getOriginalPrice());
             pe.setSize(product.getSize());
             pe.setAge(product.getAge());
             pe.setCategory(product.getCategory());
         }else {
             pe = productMapper.toEntity(product);
         }
         productDao.save(pe);
         return  pe;
    }

    @Override
    public Product findByFilter(String filter) {
        ProductEntity pe = productDao.findByBarCode(filter);
        if (pe != null) {
            return productMapper.fromEntity(pe);
        }else
            return null;
    }

    @Override
    public List<ProductEntity> getProductsForPanel() {
        return productDao.findAllByOrderByIdDesc();
    }

    @Override
    public List<ProductEntity> findAll() {
        return productDao.findAll();
    }

    @Override
    public Double getBaseTotal() {
        double total = 0.0;
        List<ProductEntity> productEntities = productDao.findAllByCountGreaterThan();
        for (ProductEntity pe : productEntities){
            if (pe.getOriginalPrice() != null) {
                total += pe.getOriginalPrice() * pe.getCount();
            }
        }
        return total;
    }

    @Override
    public Double getSellTotal() {
        double total = 0.0;
        List<ProductEntity> productEntities = productDao.findAllByCountGreaterThan();
        for (ProductEntity pe : productEntities){
            if (pe.getOriginalPrice() != null) {
                total += pe.getPrice() * pe.getCount();
            }
        }
        return total;
    }

    @Override
    public void deleteProduct(Long productId) {
        productDao.deleteById(productId);
    }

    @Override
    public Optional<ProductEntity> getProduct(Long id) {
        return productDao.findById(id);
    }


}

package kg.mara.babyfood.service.impl;

import kg.mara.babyfood.dao.OrderDao;
import kg.mara.babyfood.dao.ProductDao;
import kg.mara.babyfood.entities.OrderEntity;
import kg.mara.babyfood.entities.ProductEntity;
import kg.mara.babyfood.mapper.ProductMapper;
import kg.mara.babyfood.model.Product;
import kg.mara.babyfood.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductDao productDao;
    private final ProductMapper productMapper;

    @Override
    public Page<ProductEntity> getProducts(Pageable paging, String category) {
        if (category != null){
            return productDao.findAllByCategoryIsLikeIgnoreCaseOrderByIdAsc(paging, category);
        }else {
            return productDao.findAll(paging);
        }
    }


    @Override
    public ProductEntity saveProducts(Product product) {
         ProductEntity pe = productDao.findByBarCode(product.getBarCode());
         pe.setName(product.getName());
         pe.setNameRus(product.getNameRus());
         pe.setDescription(product.getDescription());
         pe.setCount(product.getCount());
         pe.setPrice(product.getPrice());
         pe.setOriginalPrice(product.getOriginalPrice());
         pe.setSize(product.getSize());
         pe.setAge(product.getAge());
         pe.setCategory(product.getCategory());
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
        return productDao.findTop20ByOrderByIdDesc();
    }

    @Override
    public List<ProductEntity> findAll() {
        return productDao.findAll();
    }

    @Override
    public Double getBaseTotal() {
        Double total = 0.0;
        List<ProductEntity> productEntities = productDao.findAll();
        for (ProductEntity pe : productEntities){
            total += pe.getOriginalPrice() * pe.getCount();
        }
        return total;
    }

    @Override
    public Double getSellTotal() {
        Double total = 0.0;
        List<ProductEntity> productEntities = productDao.findAll();
        for (ProductEntity pe : productEntities){
            total += pe.getPrice() * pe.getCount();
        }
        return total;
    }


}

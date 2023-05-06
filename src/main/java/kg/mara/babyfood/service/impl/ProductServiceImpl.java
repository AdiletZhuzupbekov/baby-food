package kg.mara.babyfood.service.impl;

import kg.mara.babyfood.dao.OrderDao;
import kg.mara.babyfood.dao.ProductDao;
import kg.mara.babyfood.entities.ProductEntity;
import kg.mara.babyfood.mapper.ProductMapper;
import kg.mara.babyfood.model.Product;
import kg.mara.babyfood.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
    public ProductEntity saveProducts(Product products) {
         ProductEntity pe = productMapper.toEntity(products);
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

}

package kg.mara.babyfood.service.impl;

import kg.mara.babyfood.dao.ProductDao;
import kg.mara.babyfood.entities.ProductEntity;
import kg.mara.babyfood.mapper.ProductMapper;
import kg.mara.babyfood.model.Product;
import kg.mara.babyfood.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {


    private final ProductDao productDao;
    private final ProductMapper productMapper;



    @Override
    public Page<ProductEntity> getProducts(String type, String q, PageRequest pr) {
        Page<ProductEntity> pe;
        if (type != null && q != null){
            pe = productDao.findAllByTypeIsLikeAndAndNameRusIsLikeIgnoreCase(type, q, pr);
        }
        else if ( type != null) {
            pe = productDao.findAllByTypeIsLikeIgnoreCase(type, pr);
        }else if (q != null){
            pe = productDao.findAllByNameOrNameRusIgnoreCase(q, q, pr);
        }else {
                pe = productDao.findAll(pr);
        }
        return pe;
    }


    @Override
    public void saveProducts(Product products) {
         ProductEntity pe = productMapper.toEntity(products);
            productDao.save(pe);
    }
}

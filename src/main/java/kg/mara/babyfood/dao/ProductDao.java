package kg.mara.babyfood.dao;

import kg.mara.babyfood.entities.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDao  extends PagingAndSortingRepository<ProductEntity, Long> {
    Page<ProductEntity> findAllByTypeIsLikeIgnoreCase(String type, PageRequest pr);
    Page<ProductEntity> findAllByTypeIsLikeAndAndNameRusIsLikeIgnoreCase(String type, String q, PageRequest pr);
    Page<ProductEntity> findAllByNameOrNameRusIgnoreCase(String name, String nameRus, PageRequest pr);
}

package kg.mara.babyfood.dao;

import kg.mara.babyfood.entities.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDao  extends JpaRepository<ProductEntity, Long> {
    Page<ProductEntity> findAllByCategoryIsLikeIgnoreCaseOrderByIdAsc(Pageable pr, String category);
//    Page<ProductEntity> findAllByTypeIsLikeAndAndNameRusIsLikeIgnoreCaseOrderByIdAsc(String type, String q, PageRequest pr);
//    Page<ProductEntity> findAllByNameOrNameRusIgnoreCaseOrderByIdAsc(String name, String nameRus, PageRequest pr);

    ProductEntity findByBarCode(String filter);

    List<ProductEntity> findTop20ByOrderByIdDesc();

    List<ProductEntity> findByIdIn(List<Long> ids);
}


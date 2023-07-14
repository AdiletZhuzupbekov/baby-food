package kg.mara.babyfood.dao;

import kg.mara.babyfood.entities.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDao  extends JpaRepository<ProductEntity, Long> {
    Page<ProductEntity> findAllByCategoryIsLikeIgnoreCaseOrderByIdAsc(Pageable pr, String category);
//    Page<ProductEntity> findAllByTypeIsLikeAndAndNameRusIsLikeIgnoreCaseOrderByIdAsc(String type, String q, PageRequest pr);
//    @Query("select pe from ProductEntity pe where pe.name ?1 or pe.nameRus like  ?2 order by pe.id asc ")
//    Page<ProductEntity> findAllByNameIsLikeIgnoreCaseOrderByIdAsc(String name, String nameRus, Pageable pr);

    Page<ProductEntity> findAllByNameContainingIgnoreCaseOrNameRusContainingIgnoreCase(String name,String nameRus,Pageable pr);



    ProductEntity findByBarCode(String filter);

    List<ProductEntity> findTop20ByOrderByIdDesc();

    Page<ProductEntity> findAllByNameInAndCategoryIsLike(List<String> name, String category, Pageable paging);

    Page<ProductEntity> findAllBySizeInAndCategoryIsLike(List<String> size, String category, Pageable paging);

    Page<ProductEntity> findAllByNameInAndAgeInAndCategoryIsLike(List<String> name, List<String> age, String category, Pageable paging);

    Page<ProductEntity> findAllByNameInAndSizeInAndCategoryIsLike(List<String> name, List<String> size, String category, Pageable paging);

    Page<ProductEntity> findAllByCategoryIsLikeAndNameContainingIgnoreCaseOrNameRusContainingIgnoreCase(String category, String q, String q1, Pageable paging);

    Page<ProductEntity> findAllByCountGreaterThan(int i, Pageable paging);

    @Query("select pe from ProductEntity pe where pe.count > 0")
    List<ProductEntity> findAllByCountGreaterThan();

    List<ProductEntity> findAllByOrderByIdDesc();
}


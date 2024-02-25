package kg.mara.babyfood.dao;

import kg.mara.babyfood.entities.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductDao  extends JpaRepository<ProductEntity, Long> {
    Page<ProductEntity> findAllByCountGreaterThanAndCategoryIsLikeIgnoreCaseOrderByIdAsc(int i,Pageable pr, String category);
//    Page<ProductEntity> findAllByTypeIsLikeAndAndNameRusIsLikeIgnoreCaseOrderByIdAsc(String type, String q, PageRequest pr);
//    @Query("select pe from ProductEntity pe where pe.name ?1 or pe.nameRus like  ?2 order by pe.id asc ")
//    Page<ProductEntity> findAllByNameIsLikeIgnoreCaseOrderByIdAsc(String name, String nameRus, Pageable pr);

    Page<ProductEntity> findAllByCountGreaterThanAndNameContainingIgnoreCaseOrNameRusContainingIgnoreCaseOrCriteriaContainingIgnoreCase(int i,String name,String nameRus, String criteria, Pageable pr);



    ProductEntity findByBarCode(String filter);

    List<ProductEntity> findTop20ByOrderByIdDesc();

    Page<ProductEntity> findAllByCountGreaterThanAndNameInAndCategoryIsLike(int i,List<String> name, String category, Pageable paging);

    Page<ProductEntity> findAllByCountGreaterThanAndSizeInAndCategoryIsLike(int i, List<String> size, String category, Pageable paging);

    Page<ProductEntity> findAllByCountGreaterThanAndNameInAndAgeInAndCategoryIsLike(int i,List<String> name, List<String> age, String category, Pageable paging);

    Page<ProductEntity> findAllByCountGreaterThanAndNameInAndSizeInAndCategoryIsLike(int i,List<String> name, List<String> size, String category, Pageable paging);

    Page<ProductEntity> findAllByCountGreaterThanAndCategoryIsLikeAndNameContainingIgnoreCaseOrNameRusContainingIgnoreCaseOrCriteriaContainingIgnoreCase(int i,String category, String q, String q1, String q2, Pageable paging);

    Page<ProductEntity> findAllByCountGreaterThan(int i, Pageable paging);

    @Query("select pe from ProductEntity pe where pe.count > 0")
    List<ProductEntity> findAllByCountGreaterThan();

    Page<ProductEntity> findAllByCountGreaterThanAndAgeInAndCategoryIsLike(int i,List<String> age, String category, Pageable paging);

    List<ProductEntity> findAllByOrderByIdDesc();

    List<ProductEntity> findAllByOrderByCountDesc();

    List<ProductEntity> findAllByNameContainingIgnoreCaseOrderByCategory(String filter);
}


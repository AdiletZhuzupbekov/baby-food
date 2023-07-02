package kg.mara.babyfood.dao;

import kg.mara.babyfood.entities.OrderEntity;
import kg.mara.babyfood.entities.ProductEntity;
import kg.mara.babyfood.entities.ReservedProduct;
import kg.mara.babyfood.enums.OrderType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderDao extends JpaRepository<OrderEntity, Long> {

    @Query("SELECT o FROM OrderEntity o WHERE o.orderType in ?1 order by o.id desc")
    List<OrderEntity> findByOrderType(List<OrderType> orderTypes);
    @Query("SELECT o FROM OrderEntity o WHERE o.orderDt between ?1 and ?2 and o.orderType in ?3 order by o.id desc")
    List<OrderEntity> findByDate(LocalDateTime start, LocalDateTime end, OrderType завершен);

    @Query("SELECT o FROM OrderEntity o WHERE o.orderDt between ?1 and ?2 and o.orderType in ?3 and o.driver = ?4 order by o.id desc")
    List<OrderEntity> findByDateAndDriver(LocalDateTime start, LocalDateTime end, OrderType завершен, String driver);
}

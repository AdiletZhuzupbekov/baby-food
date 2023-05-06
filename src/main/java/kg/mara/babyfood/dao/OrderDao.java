package kg.mara.babyfood.dao;

import kg.mara.babyfood.entities.OrderEntity;
import kg.mara.babyfood.entities.ProductEntity;
import kg.mara.babyfood.entities.ReservedProduct;
import kg.mara.babyfood.enums.OrderType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDao extends JpaRepository<OrderEntity, Long> {

    @Query("SELECT o FROM OrderEntity o WHERE o.orderType ='НОВЫЙ' ")
    List<OrderEntity> findByOrderType();
}

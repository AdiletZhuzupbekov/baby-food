package kg.mara.babyfood.dao;

import kg.mara.babyfood.entities.OrderEntity;
import kg.mara.babyfood.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDao extends JpaRepository<OrderEntity, Long> {
}

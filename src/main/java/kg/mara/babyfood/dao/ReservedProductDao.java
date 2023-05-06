package kg.mara.babyfood.dao;

import kg.mara.babyfood.entities.ReservedProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservedProductDao extends JpaRepository<ReservedProduct,Long> {
}

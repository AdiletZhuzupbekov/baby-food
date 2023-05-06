package kg.mara.babyfood.model;

import kg.mara.babyfood.entities.ReservedProduct;
import kg.mara.babyfood.enums.OrderType;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Order {
    private Long id;
    @OneToMany(cascade = {CascadeType.MERGE})
    List<ReservedProduct> reservedProducts;
    @Enumerated(EnumType.STRING)
    private OrderType orderType;
    private Double totalPrice;
    private LocalDateTime orderDt;

}

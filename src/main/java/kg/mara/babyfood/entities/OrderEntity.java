package kg.mara.babyfood.entities;


import kg.mara.babyfood.enums.OrderType;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ProductEntity> productEntities;
    @Enumerated(EnumType.STRING)
    private OrderType orderType;
    private Double totalPrice;


}

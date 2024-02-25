package kg.mara.babyfood.entities;


import kg.mara.babyfood.enums.OrderType;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
    List<ReservedProduct> reservedProducts;
    @Enumerated(EnumType.STRING)
    private OrderType orderType;
    private Double totalPrice;
    @DateTimeFormat(pattern = "dd-MMM-yyyy HH:mm")
    private LocalDateTime orderDt;
    private String orderId;
    private String address;
    private String driver;
    private String name;
    private String phone;


}

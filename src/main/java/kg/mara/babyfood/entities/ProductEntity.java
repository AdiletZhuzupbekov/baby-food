package kg.mara.babyfood.entities;


import kg.mara.babyfood.enums.ProductStatus;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
public class ProductEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String nameRus;
    private String size;
    private String category;
    private String age;
    private String description;
    private String image;
    private Double originalPrice;
    private Double price;
    private String barCode;
    private Integer count;
    private String criteria;
    private String deletedBy;
    private Date deletedOn;
    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @Transient
    public String getPhotoImagePath(){
        if (image == null || id == null) return null;

        return "/product-photos/" + image;
    }

}

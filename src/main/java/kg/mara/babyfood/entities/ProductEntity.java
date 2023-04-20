package kg.mara.babyfood.entities;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class ProductEntity {
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

    @Transient
    public String getPhotoImagePath(){
        if (image == null || id == null) return null;

        return "/product-photos/" + image;
    }

}

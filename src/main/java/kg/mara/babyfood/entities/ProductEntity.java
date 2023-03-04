package kg.mara.babyfood.entities;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String nameRus;
    private String size;
    private String type;
    private String description;
    private String image;
    private Double price;
    private String barCode;

}

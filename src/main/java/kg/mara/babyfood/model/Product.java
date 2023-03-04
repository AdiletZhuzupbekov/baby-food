package kg.mara.babyfood.model;

import lombok.Data;

@Data
public class Product {
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

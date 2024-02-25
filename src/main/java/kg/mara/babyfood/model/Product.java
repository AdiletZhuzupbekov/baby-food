package kg.mara.babyfood.model;

import lombok.Data;

import javax.persistence.Transient;

@Data
public class Product {
    private Long id;
    private String name;
    private String nameRus;
    private String size;
    private String age;
    private String category;
    private String description;
    private String image;
    private Double originalPrice;
    private Double price;
    private String barCode;
    private Integer count;
    private String criteria;

    @Transient
    public String getPhotoImagePath(){
        if (image == null || id == null) return null;

        return "/product-photos/" + id + "/" + image;
    }
}

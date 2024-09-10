package com.eni.pizzaWebsite.bo;

public class Product {

    private Long id_product;
    private String name ;
    private String description ;
    private float price ;
    private String image_url ;
    private Long size ;

    public Product(Long id_product, String name, String description, float price, String image_url, Long size) {
        this.id_product = id_product;
        this.name = name;
        this.description = description;
        this.price = price;
        this.image_url = image_url;
        this.size = size;
    }

    public Long getId_product() {
        return id_product;
    }

    public void setId_product(Long id_product) {
        this.id_product = id_product;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }
}

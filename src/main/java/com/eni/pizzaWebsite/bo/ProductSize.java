package com.eni.pizzaWebsite.bo;

public class ProductSize {
    private int id_size;
    private float price_difference;

    public ProductSize() {
        super();
    }

    public ProductSize(int id_size, float price_difference) {
        this.id_size = id_size;
        this.price_difference = price_difference;
    }

    public int getId_size() {
        return id_size;
    }

    public void setId_size(int id_size) {
        this.id_size = id_size;
    }

    public float getPrice_difference() {
        return price_difference;
    }

    public void setPrice_difference(float price_difference) {
        this.price_difference = price_difference;
    }
}

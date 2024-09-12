package com.eni.pizzaWebsite.bo;

public class OrderDetail {

    private Long id_order;
    private Product product;
    private int size;
    private int quantity;

    public OrderDetail() {
        super();
    }

    public OrderDetail(Long id_order, Product product, int size, int quantity) {
        this.id_order = id_order;
        this.product = product;
        this.size = size;
        this.quantity = quantity;
    }

    public Long getId_order() {

        return id_order;
    }

    public void setId_order(Long id_order) {

        this.id_order = id_order;
    }

    public int getQuantity() {

        return quantity;
    }

    public void setQuantity(int quantity) {

        this.quantity = quantity;
    }

    public Product getProduct() {

        return product;
    }

    public void setProduct(Product product) {

        this.product = product;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}

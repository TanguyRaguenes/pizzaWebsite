package com.eni.pizzaWebsite.bo;

public class OrderDetail {

    private Long id_order;
    private Product product;
    private int quantity;

    public OrderDetail() {
        super();
    }

    public OrderDetail(Long id_order, Product product, int quantity) {
        this.id_order = id_order;
        this.product = product;
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
}

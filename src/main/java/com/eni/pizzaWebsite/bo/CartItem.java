package com.eni.pizzaWebsite.bo;

public class CartItem {
    private Product product;
    private int quantity;
    private int size;

    public CartItem(Product product, int quantity, int size) {
        this.product = product;
        this.quantity = quantity;
        this.size = size;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public double getTotalPrice() {
        double basePrice = product.getPrice();
        if (size == 2) {
            basePrice += 3;
        }
        return basePrice * quantity;
    }
}

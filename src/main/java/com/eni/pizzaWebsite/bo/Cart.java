package com.eni.pizzaWebsite.bo;


import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;

@Component
@SessionScope
public class Cart {

    private List<CartItem> items = new ArrayList<>();

    public List<CartItem> getItems() {
        return items;
    }


    public void addProduct(Product product, int quantity, int size) {
        items.add(new CartItem(product, quantity, size));
    }


    public void removeProductById(Long id_product) {
        items.removeIf(item -> item.getProduct().getId_product().equals(id_product));
    }


    public void clearCart() {
        items.clear();
    }
}

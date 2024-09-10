package com.eni.pizzaWebsite.model;

import com.eni.pizzaWebsite.bo.Product;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;

@Component
@SessionScope
public class Cart {

    private List<Product> products = new ArrayList<>();

    public List<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProductById(Long id_product) {
        products.removeIf(product -> product.getId_product().equals(id_product));
    }

    public void clearCart() {
        products.clear();
    }
}

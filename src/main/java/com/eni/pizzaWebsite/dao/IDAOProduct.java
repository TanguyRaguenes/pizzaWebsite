package com.eni.pizzaWebsite.dao;

import com.eni.pizzaWebsite.bo.Product;

import java.util.List;

public interface IDAOProduct {

    public abstract List<Product> getProductsList();
    public abstract Product getProductById(Long id_product );
    public abstract void addProductToList(Product product);
    public abstract void deleteProductFromList(Long product);

}

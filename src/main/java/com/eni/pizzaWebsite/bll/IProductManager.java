package com.eni.pizzaWebsite.bll;

import com.eni.pizzaWebsite.bo.Product;

import java.util.List;

public interface IProductManager {

    public abstract List<Product> getProductsList();
    public abstract Product getProductById(Long id_product);
    public abstract void addProductToList(Product product);
    public abstract void deleteProductFromList(Long id_product);

}

package com.eni.pizzaWebsite.bll;

import com.eni.pizzaWebsite.bo.Product;

public interface IOrderManager {

    public abstract void addProductToOrder(Product product, Long idClient, Long quantity);

}

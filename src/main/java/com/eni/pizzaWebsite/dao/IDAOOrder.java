package com.eni.pizzaWebsite.dao;

import com.eni.pizzaWebsite.bo.Order;
import com.eni.pizzaWebsite.bo.Product;

public interface IDAOOrder {

    public abstract void addProductToOrder(Product product,Long idClient,Long quantity);
    public abstract Order getClientOrderId(Long id_client );

}

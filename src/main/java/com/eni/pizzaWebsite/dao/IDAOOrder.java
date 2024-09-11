package com.eni.pizzaWebsite.dao;

import com.eni.pizzaWebsite.bo.Order;
import com.eni.pizzaWebsite.bo.Product;

public interface IDAOOrder {

    public abstract void addOrder(Order order);
    public abstract Order getOrderById(Long id_order );

}

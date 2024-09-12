package com.eni.pizzaWebsite.dao;

import com.eni.pizzaWebsite.bo.Order;
import com.eni.pizzaWebsite.bo.OrderDetail;
import com.eni.pizzaWebsite.bo.Product;

import java.util.List;

public interface IDAOOrder {

    public abstract void addProductToOrder(Product product,Long id_client,Long quantity);
    public abstract Order getClientOrderId(Long id_client );
    public abstract List<OrderDetail> getOrderDetail(Long id_client);
    public abstract void removeProductFromOrder(Long id_product,Long id_client);

}

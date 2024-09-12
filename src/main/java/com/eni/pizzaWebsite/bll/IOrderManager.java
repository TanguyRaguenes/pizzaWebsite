package com.eni.pizzaWebsite.bll;

import com.eni.pizzaWebsite.bo.OrderDetail;
import com.eni.pizzaWebsite.bo.Product;

import java.util.List;

public interface IOrderManager {

    public abstract void addProductToOrder(Product product, Long id_client, Long quantity,Long size);
    public abstract List<OrderDetail> getOrderDetail(Long id_client);
    public abstract void removeProductFromOrder(Long id_product,Long id_client,Long id_size);
    public abstract Float getOrderTotalPrice(Long id_client);
}

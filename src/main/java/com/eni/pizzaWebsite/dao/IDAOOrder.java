package com.eni.pizzaWebsite.dao;

import com.eni.pizzaWebsite.bo.Order;
import com.eni.pizzaWebsite.bo.OrderDetail;
import com.eni.pizzaWebsite.bo.Product;
import com.eni.pizzaWebsite.bo.ProductSize;

import java.time.LocalDateTime;
import java.util.List;

public interface IDAOOrder {

    public abstract void addProductToOrder(Product product, Long id_client, Long quantity,Long size);
    public abstract Order getOrder(Long id_client, Long id_order );
    public abstract OrderDetail getOrderDetail(Long id_order,Long id_product, Long id_size );
    public abstract List<OrderDetail> getOrderDetail(Long id_client);
    public abstract void removeProductFromOrder(Long id_product,Long id_client, Long id_size);
    public abstract Float getOrderTotalPrice(Long id_client);
    public abstract void checkout(Long id_client, Long id_order, LocalDateTime delivery_datetime);

    public abstract void clearOrderForClient(Long id_client);
    public abstract List<ProductSize> getPriceByProductSize();
    public abstract List<Order> getOrdersList(Long id_state);
}

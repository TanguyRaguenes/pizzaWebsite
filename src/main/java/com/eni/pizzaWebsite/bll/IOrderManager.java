package com.eni.pizzaWebsite.bll;

import com.eni.pizzaWebsite.bo.*;

import java.time.LocalDateTime;
import java.util.List;

public interface IOrderManager {

    public abstract void addProductToOrder(Product product, Long id_client, Long quantity, Long size);

    public abstract void addOrderDetailToOrder(Product product, Long id_order, Long size,Long quantity) ;

    public abstract void removeOrderDetailToOrder(Long id_order, Long id_product, Long id_size) ;

    public abstract List<OrderDetail> getOrderDetailsByIdOrder(Long id_order);

    public abstract void updateOrderDeliveryDate(Long id_order, LocalDateTime delivery_datetime);


    public abstract List<OrderDetail> getOrderDetail(Long id_client);

    public abstract void removeProductFromOrder(Long id_product, Long id_client, Long id_size);

    public abstract Float getOrderTotalPrice(Long id_client);

    public abstract void checkout(Long id_client, Long id_order, LocalDateTime delivery_datetime);

    public abstract void clearOrderForClient(Long id_client);

    public abstract List<ProductSize> getPriceByProductSize();

    public abstract List<Order> getOrdersList(Long id_state);


    public abstract Order getOrder(Long id_client, Long id_order);

    public abstract void clearOrderByIdOrder(Long id_order);

    public abstract void updateOrderState(Long id_order, Long id_state);

    public abstract List<State> getStatesList();
}

package com.eni.pizzaWebsite.bll;

import com.eni.pizzaWebsite.bo.*;
import com.eni.pizzaWebsite.dao.IDAOOrder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class OrderManager implements IOrderManager {

    private IDAOOrder orderDao;

    public OrderManager(IDAOOrder orderDao) {

        this.orderDao = orderDao;
    }

    @Override
    public void addProductToOrder(Product product, Long id_client, Long quantity, Long size) {
        orderDao.addProductToOrder(product, id_client, quantity, size);
    }

    @Override
    public void addOrderDetailToOrder(Product product, Long id_order, Long size, Long quantity) {
        orderDao.addOrderDetailToOrder(product, id_order, size, quantity);
    }

    @Override
    public void removeOrderDetailToOrder(Long id_order, Long id_product, Long id_size) {
        orderDao.removeOrderDetailToOrder(id_order, id_product, id_size);
    }

    @Override
    public List<OrderDetail> getOrderDetail(Long id_client) {
        return orderDao.getOrderDetail(id_client);
    }

    @Override
    public void removeProductFromOrder(Long id_product, Long id_client, Long id_size) {
        orderDao.removeProductFromOrder(id_product, id_client, id_size);
    }

    @Override
    public Float getOrderTotalPrice(Long id_client) {
        return orderDao.getOrderTotalPrice(id_client);
    }

    @Override
    public void checkout(Long id_client, Long id_order, LocalDateTime delivery_datetime) {
        orderDao.checkout(id_client, id_order, delivery_datetime);
    }

    @Override
    public void clearOrderForClient(Long id_client) {
        orderDao.clearOrderForClient(id_client);
    }

    @Override
    public List<ProductSize> getPriceByProductSize() {
        return orderDao.getPriceByProductSize();
    }

    @Override
    public List<Order> getOrdersList(Long id_state) {
        return orderDao.getOrdersList(id_state);
    }

    @Override
    public Order getOrder(Long id_client, Long id_order) {
        return orderDao.getOrder(id_client, id_order);
    }

    @Override
    public void clearOrderByIdOrder(Long id_order){orderDao.clearOrderByIdOrder(id_order);}

    @Override
    public void updateOrderState (Long id_order, Long id_state){orderDao.updateOrderState(id_order, id_state);}

    @Override
    public List<State> getStatesList() {return orderDao.getStatesList();}
}



package com.eni.pizzaWebsite.bll;

import com.eni.pizzaWebsite.bo.OrderDetail;
import com.eni.pizzaWebsite.bo.Product;
import com.eni.pizzaWebsite.dao.IDAOOrder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderManager implements IOrderManager{

    private IDAOOrder orderDao;

    public OrderManager(IDAOOrder orderDao) {
        this.orderDao = orderDao;
    }

    @Override

    public void addProductToOrder(Product product, Long id_client, Long quantity) {
        orderDao.addProductToOrder(product, id_client, quantity);
    }

    @Override
    public List<OrderDetail> getOrderDetail(Long id_client) {
        return orderDao.getOrderDetail(id_client);
    }

}

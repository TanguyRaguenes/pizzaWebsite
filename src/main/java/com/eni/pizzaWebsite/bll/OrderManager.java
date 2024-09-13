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
    public void addProductToOrder(Product product, Long id_client, Long quantity,Long size) {
        orderDao.addProductToOrder(product, id_client, quantity, size);
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
    public void checkout(Long id_client) {
        orderDao.checkout(id_client);
    }

    @Override
    public void clearOrderForClient(Long id_client) {
        orderDao.clearOrderForClient(id_client);
    }

}

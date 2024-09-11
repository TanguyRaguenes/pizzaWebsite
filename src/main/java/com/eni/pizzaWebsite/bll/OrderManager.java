package com.eni.pizzaWebsite.bll;

import com.eni.pizzaWebsite.bo.Product;
import com.eni.pizzaWebsite.dao.IDAOOrder;
import org.springframework.stereotype.Component;

@Component
public class OrderManager implements IOrderManager{

    private IDAOOrder orderDao;

    public OrderManager(IDAOOrder orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public void addProductToOrder(Product product, Long idClient,int quantity ) {
        orderDao.addProductToOrder(product, idClient);
    }
}

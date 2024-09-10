package com.eni.pizzaWebsite.bll;

import com.eni.pizzaWebsite.bo.Product;
import com.eni.pizzaWebsite.dao.IDAOProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductManager implements IProductManager {

    @Autowired
    private IDAOProduct productDAO;



    public void save(Product product) {
        productDAO.save(product);
    }
}

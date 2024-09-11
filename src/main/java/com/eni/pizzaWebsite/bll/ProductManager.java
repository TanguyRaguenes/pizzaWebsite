package com.eni.pizzaWebsite.bll;

import com.eni.pizzaWebsite.bo.Product;
import com.eni.pizzaWebsite.dao.IDAOProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductManager implements IProductManager {

    private IDAOProduct productDAO;

    public ProductManager(IDAOProduct productDAO) {
        this.productDAO = productDAO;
    }


    @Override
    public List<Product> getProductsList() {
        return productDAO.getProductsList();
    }

    @Override
    public void addProductToList(Product product) {
        productDAO.addProductToList(product);
    }

    @Override
    public void deleteProductFromList(Long id_product) {
        productDAO.deleteProductFromList(id_product);
    }

    @Override
    public Product getProductById(Long id_product) {
        return productDAO.getProductById(id_product);
    }
}


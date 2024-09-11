package com.eni.pizzaWebsite.ihm.converter;

import com.eni.pizzaWebsite.bll.IProductManager;
import com.eni.pizzaWebsite.bo.Client;
import com.eni.pizzaWebsite.bo.Product;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class idToProductConverter implements Converter<Long, Product> {

    private IProductManager productManager;

    public idToProductConverter(IProductManager productManager) {
        this.productManager = productManager;
    }

    @Override
    public Product convert(Long id_product) {
        return productManager.getProductById(id_product);
    }
}

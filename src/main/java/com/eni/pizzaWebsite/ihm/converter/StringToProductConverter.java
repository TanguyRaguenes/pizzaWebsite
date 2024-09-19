package com.eni.pizzaWebsite.ihm.converter;

import com.eni.pizzaWebsite.bll.IProductManager;
import com.eni.pizzaWebsite.bo.Product;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


//Converter = interface générique
@Component
public class StringToProductConverter implements Converter<String, Product> {

    private IProductManager productManager;

    public StringToProductConverter (IProductManager productManager) {
        this.productManager = productManager;
    }

    @Override
    public Product convert(String id_product) {

        System.out.println("Conversion de id_product : " + id_product);

        Product product = productManager.getProductById(Long.parseLong(id_product));
        return product;
    }

}
package com.eni.pizzaWebsite.ihm.converter;

import com.eni.pizzaWebsite.bll.IClientManager;
import com.eni.pizzaWebsite.bll.IProductManager;
import com.eni.pizzaWebsite.bo.Client;
import com.eni.pizzaWebsite.bo.Product;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


//Converter = interface générique
@Component
public class StringToClientConverter implements Converter<String, Client> {

    private IClientManager clientManager;

    public StringToClientConverter(IClientManager clientManager) {
        this.clientManager = clientManager;
    }

    @Override
    public Client convert(String id_client) {

        System.out.println("Conversion de id_client : " + id_client);

        Client client = clientManager.getClientById(Long.parseLong(id_client));
        return client;
    }

}
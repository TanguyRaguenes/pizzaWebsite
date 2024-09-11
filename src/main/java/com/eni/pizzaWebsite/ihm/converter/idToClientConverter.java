package com.eni.pizzaWebsite.ihm.converter;

import com.eni.pizzaWebsite.bo.Client;
import com.eni.pizzaWebsite.bo.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class idToClientConverter implements Converter<Long, Client> {
    @Override
    public Client convert(Long source) {
        return null;
    }
}

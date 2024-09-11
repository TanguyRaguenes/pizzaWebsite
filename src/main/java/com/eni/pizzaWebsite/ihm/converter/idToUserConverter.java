package com.eni.pizzaWebsite.ihm.converter;

import com.eni.pizzaWebsite.bo.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class idToUserConverter implements Converter<Long, User> {
    @Override
    public User convert(Long source) {
        return null;
    }
}

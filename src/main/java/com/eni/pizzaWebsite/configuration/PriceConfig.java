package com.eni.pizzaWebsite.configuration;

import com.eni.pizzaWebsite.bll.IOrderManager;
import com.eni.pizzaWebsite.bo.Prices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class PriceConfig {

    @Autowired
    IOrderManager orderManager;

    @Bean
    public Prices getPrices() {
        return new Prices(orderManager);
    }

}

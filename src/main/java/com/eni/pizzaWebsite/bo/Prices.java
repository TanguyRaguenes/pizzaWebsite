package com.eni.pizzaWebsite.bo;

import com.eni.pizzaWebsite.bll.IOrderManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Component
public class Prices {


    private IOrderManager orderManager;

    private Map<Integer,Float> mapPrices;
    private List<ProductSize> productsSizes;

    public Prices(IOrderManager orderManager){
        super();
        this.orderManager = orderManager;
        this.mapPrices = new HashMap<Integer,Float>();
        this.productsSizes = orderManager.getPriceByProductSize();
        productsSizes.forEach(productSize -> {
            mapPrices.put(productSize.getId_size(), productSize .getPrice_difference());
        });
        System.out.println("prices: " + mapPrices);

    }

    public Float getPrice(Integer id){
        return mapPrices.get(id);
    }

}

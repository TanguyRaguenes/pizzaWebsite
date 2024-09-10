package com.eni.pizzaWebsite.ihm;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductController {

    @GetMapping("/")
    public String home(){return "index";}

    @GetMapping("product-form")
    public String productForm(){
        return "product-form";
    }




}

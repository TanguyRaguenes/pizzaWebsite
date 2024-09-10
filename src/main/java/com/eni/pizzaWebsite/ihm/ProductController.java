package com.eni.pizzaWebsite.ihm;

import com.eni.pizzaWebsite.bll.ProductManager;
import com.eni.pizzaWebsite.bo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class ProductController {

    @Autowired
    private ProductManager productManager;

    @GetMapping("/")
    public String home(){return "index";}

    @GetMapping("/product-form")
    public String productForm(){
        return "product-form";
    }

    @PostMapping("product-form")
    public String postProductForm(@ModelAttribute Product product, BindingResult bindingResult) {

        productManager.save(product);

        return "redirect:/index";
    }


}

package com.eni.pizzaWebsite.ihm;

import com.eni.pizzaWebsite.bll.IProductManager;
import com.eni.pizzaWebsite.bll.ProductManager;
import com.eni.pizzaWebsite.bo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

import java.util.List;


@Controller
public class ProductController {

    @Autowired
    private IProductManager productManager;

    @GetMapping("/")
    public String home(){return "index";}

    @GetMapping("/products-list")
    public String productsList(Model model){
        List<Product> productList = productManager.getProductsList();
        model.addAttribute("products", productList);
        return "products-list";
    }

    @GetMapping("/product-form")
    public String productForm(){
        return "product-form";
    }

    @PostMapping("product-form")
    public String postProductForm(@ModelAttribute Product product, BindingResult bindingResult) {

        productManager.addProductToList(product);

        return "redirect:/index";
    }


}

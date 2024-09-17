package com.eni.pizzaWebsite.ihm;

import com.eni.pizzaWebsite.bll.IProductManager;
import com.eni.pizzaWebsite.bll.ProductManager;
import com.eni.pizzaWebsite.bo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

import java.util.List;


@Controller
public class ProductController {

    @Autowired
    private IProductManager productManager;

    @GetMapping("/products-list")
    public String productsList(Model model) {
        List<Product> productList = productManager.getProductsList();
        model.addAttribute("products", productList);
        return "products-list";
    }

    @GetMapping("/product-form")
    public String productForm(Model model) {

        Product product = new Product();
        model.addAttribute("product", product);
        return "product-form";
    }

    @GetMapping("/product-form/{id}")
    public String editProductForm(@PathVariable("id") Long id, Model model) {
        Product product = productManager.getProductById(id);
        model.addAttribute("product", product);
        return "product-form";
    }

    @PostMapping("/product-form")
    public String postProductForm(@ModelAttribute Product product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "product-form";
        }
        System.out.println("Product submitted: " +  product.toString());
        productManager.addProductToList(product);

        return "redirect:/products-list";
    }

    @GetMapping("/products-list/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id_product) {
        productManager.deleteProductFromList(id_product);
        return "redirect:/products-list";
    }

}

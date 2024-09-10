package com.eni.pizzaWebsite.ihm;

import com.eni.pizzaWebsite.bll.IProductManager;
import com.eni.pizzaWebsite.bll.ProductManager;
import com.eni.pizzaWebsite.bo.Product;
import com.eni.pizzaWebsite.model.Cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;

@Controller
public class CartController {

    @Autowired
    private IProductManager productManager;


    private Cart cart;

    public CartController(ProductManager productManager, Cart cart) {
        this.productManager = productManager;
        this.cart = cart;
    }


    @GetMapping("/cart/add/{id}")
    public String addProductToCart(@PathVariable("id") Long id) {
        Product product = productManager.getProductById(id);
        cart.addProduct(product);
        return "redirect:/products-list";
    }


    @GetMapping("/cart")
    public String viewCart(Model model) {
        model.addAttribute("cartItems", cart.getProducts());
        return "cart";
    }

    @GetMapping("/cart/remove/{id}")
    public String removeProductFromCart(@PathVariable("id") Long id) {
        cart.removeProductById(id);
        return "redirect:/cart";
    }


    @GetMapping("/cart/clear")
    public String clearCart() {
        cart.clearCart();
        return "redirect:/";
    }




}

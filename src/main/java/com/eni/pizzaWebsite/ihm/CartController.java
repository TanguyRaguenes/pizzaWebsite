package com.eni.pizzaWebsite.ihm;

import com.eni.pizzaWebsite.bll.IProductManager;
import com.eni.pizzaWebsite.bo.Product;
import com.eni.pizzaWebsite.bo.Cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@SessionAttributes("cart")
@Controller
public class CartController {

    @Autowired
    private IProductManager productManager;

    @Autowired
    private Cart cart;

    @GetMapping("/cart")
    public String viewCart(Model model) {
        model.addAttribute("cart", cart);
        model.addAttribute("cartItems", cart.getItems());
        return "cart";
    }

    @PostMapping("/cart/add/{id}")
    public String addProductToCart(@PathVariable("id") Long id,
                                   @RequestParam("quantity") int quantity,
                                   @RequestParam("size") int size) {
        Product product = productManager.getProductById(id);
        cart.addProduct(product, quantity, size); //
        return "redirect:/products-list";
    }



    @GetMapping("/cart/remove/{id}")
    public String removeProductFromCart(@PathVariable("id") Long id) {
        cart.removeProductById(id);
        return "redirect:/cart";  // Redirection vers la page du panier après suppression
    }


    @GetMapping("/cart/clear")
    public String clearCart() {
        cart.clearCart();
        return "redirect:/";
    }
}





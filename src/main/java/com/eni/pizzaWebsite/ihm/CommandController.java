package com.eni.pizzaWebsite.ihm;

import com.eni.pizzaWebsite.bll.IProductManager;
import com.eni.pizzaWebsite.bo.Order;
import com.eni.pizzaWebsite.bo.OrderDetail;
import com.eni.pizzaWebsite.bo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@SessionAttributes("order")
@Controller
public class CommandController {

    @Autowired
    private IProductManager productManager;


    @ModelAttribute("order")
    public Order createOrder() {
        return new Order();
    }

    @GetMapping("/cart")
    public String viewCart(Model model, @ModelAttribute("order") Order order) {
        model.addAttribute("orderDetails", order.getOrderDetails());
        return "cart";
    }

    @PostMapping("/cart/add/{id}")
    public String addProductToCart(@PathVariable("id") Long id,
                                   @RequestParam("quantity") int quantity,
                                   @RequestParam("size") int size,
                                   @ModelAttribute("order") Order order) {
        Product product = productManager.getProductById(id);
        //orderM.addItemToOrder(id,1)
        order.getOrderDetails().add(new OrderDetail(order.getId_order(), product, quantity));
        return "redirect:/products-list";
    }

    @GetMapping("/cart/remove/{id}")
    public String removeProductFromCart(@PathVariable("id") Long id, @ModelAttribute("order") Order order) {
        order.getOrderDetails().removeIf(item -> item.getProduct().getId_product().equals(id));
        return "redirect:/cart";
    }

    @GetMapping("/cart/clear")
    public String clearCart(@ModelAttribute("order") Order order) {
        order.getOrderDetails().clear();
        return "redirect:/";
    }
    // Augmenter la quantité d'un produit dans le panier
    @GetMapping("/cart/increaseQuantity/{id}")
    public String increaseProductQuantity(@PathVariable("id") Long id, @ModelAttribute("order") Order order) {
        for (OrderDetail detail : order.getOrderDetails()) {
            if (detail.getProduct().getId_product().equals(id)) {
                detail.setQuantity(detail.getQuantity() + 1); // Augmenter la quantité
                break;
            }
        }
        return "redirect:/cart";
    }

    // Diminuer la quantité d'un produit dans le panier
    @GetMapping("/cart/decreaseQuantity/{id}")
    public String decreaseProductQuantity(@PathVariable("id") Long id, @ModelAttribute("order") Order order) {
        for (OrderDetail detail : order.getOrderDetails()) {
            if (detail.getProduct().getId_product().equals(id)) {
                if (detail.getQuantity() > 1) {
                    detail.setQuantity(detail.getQuantity() - 1); // Diminuer la quantité
                } else {
                    order.getOrderDetails().remove(detail); // Retirer l'élément si la quantité est 1
                }
                break;
            }
        }
        return "redirect:/cart";
    }
}

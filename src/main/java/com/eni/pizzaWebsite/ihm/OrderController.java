package com.eni.pizzaWebsite.ihm;

import com.eni.pizzaWebsite.bll.IOrderManager;
import com.eni.pizzaWebsite.bll.IProductManager;
import com.eni.pizzaWebsite.bo.Order;
import com.eni.pizzaWebsite.bo.OrderDetail;
import com.eni.pizzaWebsite.bo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@SessionAttributes("order")
@Controller
public class OrderController {

    @Autowired
    private IProductManager productManager;

    @Autowired
    private IOrderManager orderManager;


    @ModelAttribute("order")
    public Order createOrder() {
        return new Order();
    }

    @GetMapping("/cart")
    public String viewCart(Model model, @ModelAttribute("order") Order order) {
        List<OrderDetail> orderDetails=orderManager.getOrderDetail(1L);
        model.addAttribute("orderDetails",orderDetails);
        return "cart";
    }



    @PostMapping("/cart/add/{id}")
    public String addProductToCart(@PathVariable("id") Long id,
                                   @RequestParam("quantity") Long quantity) {
        Product product = productManager.getProductById(id);
        System.out.println(product);
        orderManager.addProductToOrder(product, 1L, quantity);
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

}

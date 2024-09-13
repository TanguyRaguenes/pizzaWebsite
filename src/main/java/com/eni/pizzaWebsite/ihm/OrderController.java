package com.eni.pizzaWebsite.ihm;

import com.eni.pizzaWebsite.bll.IOrderManager;
import com.eni.pizzaWebsite.bll.IProductManager;
import com.eni.pizzaWebsite.bo.Order;
import com.eni.pizzaWebsite.bo.OrderDetail;
import com.eni.pizzaWebsite.bo.Product;
import com.eni.pizzaWebsite.configuration.PriceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


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
    public String viewCart(Model model, @ModelAttribute("orderDetail") OrderDetail orderDetail) {

        List<OrderDetail> orderDetails = orderManager.getOrderDetail(1L);
        model.addAttribute("orderDetails", orderDetails);

        Float totalPrice = orderManager.getOrderTotalPrice(1L);
        model.addAttribute("totalPrice", totalPrice);

        return "cart";
    }


    @PostMapping("/cart/add/{id}")
    public String addProductToCart(@PathVariable("id") Long id,
                                   @RequestParam("quantity") Long quantity,
                                   @RequestParam("size") Long size) {
        Product product = productManager.getProductById(id);
        System.out.println(product);
        orderManager.addProductToOrder(product, 1L, quantity, size);
        return "redirect:/products-list";
    }

    @GetMapping("/cart/remove/{id}")
    public String removeProductFromCart(@PathVariable("id") Long id_product,
                                        @RequestParam("size") Long size) {
        orderManager.removeProductFromOrder(id_product, 1L, size);
        return "redirect:/cart";
    }

    @GetMapping("/cart/checkout")
    public String checkout() {
        orderManager.checkout(1L);
        return "redirect:/cart";
        //créer une page de validation de la commande ?
    }


    @GetMapping("/cart/clear")
    public String clearCart() {
        orderManager.clearOrderForClient(1L);
        return "redirect:/cart";
    }


}

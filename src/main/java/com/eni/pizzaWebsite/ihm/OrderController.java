package com.eni.pizzaWebsite.ihm;

import com.eni.pizzaWebsite.bll.IOrderManager;
import com.eni.pizzaWebsite.bll.IProductManager;
import com.eni.pizzaWebsite.bo.Order;
import com.eni.pizzaWebsite.bo.OrderDetail;
import com.eni.pizzaWebsite.bo.Product;
import com.eni.pizzaWebsite.configuration.PriceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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


        if (orderDetails != null) {
            for (OrderDetail detail : orderDetails) {
                Float priceDifference = detail.getPrice_difference();
                Float adjustedPrice = detail.getProduct().getPrice() + priceDifference;
                detail.getProduct().setPrice(adjustedPrice);
            }
            model.addAttribute("orderDetails", orderDetails);
            Float totalPrice = orderManager.getOrderTotalPrice(1L);
            model.addAttribute("totalPrice", totalPrice);
        } else {
            model.addAttribute("orderDetails", null);
            model.addAttribute("totalPrice", 0);
        }
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
        orderManager.checkout(1L, LocalDateTime.now());
        return "orders-list";
    }


    @PostMapping("/cart/checkout")
    public String checkout(@RequestParam("deliveryDatetime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime deliveryDatetime,
                           Model model) {
        Long id_client = 1L;
        orderManager.checkout(id_client, deliveryDatetime);
        return "orders-list";
    }

    @GetMapping("/cart/clear")
    public String clearCart() {
        orderManager.clearOrderForClient(1L);
        return "redirect:/cart";
    }

    @GetMapping("/orders-list")
    public String viewOrders(@RequestParam(name = "status", required = false, defaultValue = "0") Long id_state, Model model) {
        List<Order> orders = orderManager.getOrdersList(id_state);
        model.addAttribute("orders", orders);
        return "orders-list";
    }

}

package com.eni.pizzaWebsite.ihm;

import com.eni.pizzaWebsite.bll.IClientManager;
import com.eni.pizzaWebsite.bll.IOrderManager;
import com.eni.pizzaWebsite.bll.IProductManager;
import com.eni.pizzaWebsite.bo.Client;
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

    @Autowired
    private IClientManager clientManager;

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

    @PostMapping("/cart/add/{id_product}")
    public String addProductToCartOrOrder(@PathVariable("id_product") Long id_product,
                                          @RequestParam("quantity") Long quantity,
                                          @RequestParam("size") Long size,
                                          @RequestParam(value = "id_order", required = false) Long id_order) {
        Product product = productManager.getProductById(id_product);

        if (id_order != null) {
            orderManager.addProductToOrder(product, id_order, quantity, size);
            return "redirect:/orders/edit/" + id_order;
        } else {
            orderManager.addProductToOrder(product, 1L, quantity, size);
            return "redirect:/products-list";
        }
    }

    @GetMapping("/orders/edit/{id_order}")
    public String editOrder(@PathVariable("id_order") Long id_order, Model model) {
        List<Order> orders = orderManager.getOrdersList(0L);
        Order selectedOrder = orders.stream()
                .filter(order -> order.getId_order().equals(id_order))
                .findFirst()
                .orElse(null);

        if (selectedOrder != null) {
            model.addAttribute("order", selectedOrder);
            List<Product> products = productManager.getProductsList();
            model.addAttribute("products", products);
            return "edit-order-products";
        } else {
            return "redirect:/orders-list";
        }
    }

    @GetMapping("/cart/remove/{id}")
    public String removeProductFromCart(@PathVariable("id") Long id_product,
                                        @RequestParam("size") Long size) {
        orderManager.removeProductFromOrder(id_product, 1L, size);
        return "redirect:/cart";
    }

//    @GetMapping("/cart/checkout")
//    public String checkout() {
//        orderManager.checkout(1L, LocalDateTime.now());
//        return "redirect:/orders-list";
//    }

//    @PostMapping("/cart/checkout")
//    public String checkout(@RequestParam("deliveryDatetime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime deliveryDatetime,
//                           Model model) {
//        Long id_client = 1L;
//        orderManager.checkout(id_client, deliveryDatetime);
//        return "redirect:/orders-list";
//    }
    @PostMapping("/orders/checkout/{id_order}")
    public String checkoutOrder(@PathVariable("id_order") Long id_order) {
        LocalDateTime deliveryDatetime = LocalDateTime.now().plusMinutes(30); // Définir une date de livraison par défaut
        orderManager.checkout(null, id_order, deliveryDatetime); // Utiliser l'ID de la commande pour faire le checkout
        return "redirect:/orders-list"; // Redirection vers la liste des commandes après le checkout
    }

    @GetMapping("/cart/clear")
    public String clearCart() {
        orderManager.clearOrderForClient(1L);
        return "redirect:/cart";
    }

    // Liste des commandes et drop down menu pour sélectionner client
    @GetMapping("/orders-list")
    public String showOrdersList(Model model, @RequestParam(value = "state", required = false) Long id_state) {
        if (id_state == null) {
            id_state = 0L;
        }
        List<Order> orders = orderManager.getOrdersList(id_state);
        model.addAttribute("orders", orders);

        List<Client> clients = clientManager.getClientsList();
        model.addAttribute("clients", clients);

        return "orders-list";
    }

    @PostMapping("/orders-list/create")
    public String createOrder(@RequestParam("id_client") Long id_client) {
        Long id_user = 1L;
        orderManager.addProductToOrder(null, id_client, 0L, 0L);
        return "redirect:/orders-list";
    }
}

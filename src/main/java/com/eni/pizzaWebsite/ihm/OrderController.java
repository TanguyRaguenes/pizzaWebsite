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
    public String viewCart(Model model, @ModelAttribute("orderDetail") OrderDetail orderDetail) {
        List<OrderDetail> orderDetails = orderManager.getOrderDetail(1L);
        model.addAttribute("orderDetails", orderDetails);
        return "cart";
    }


    //utiliser ce controller pour modifier la quantité d'un produit dans le panier
    @PostMapping("/cart/add/{id}")
    public String addProductToCart(@PathVariable("id") Long id,
                                   @RequestParam("quantity") Long quantity,
                                    @RequestParam("size")Long size){
        Product product = productManager.getProductById(id);
        System.out.println(product);
        orderManager.addProductToOrder(product, 1L, quantity, size);
        return "redirect:/products-list";
    }

    @GetMapping("/cart/remove/{id}")
    public String removeProductFromCart(@PathVariable("id") Long id_product,
                                        @RequestParam("id_client") Long id_client) {
        orderManager.removeProductFromOrder(id_product, id_client);
        return "redirect:/cart";
    }


    @GetMapping("/cart/clear")
    public String clearCart(@ModelAttribute("order") Order order) {
        order.getOrderDetails().clear();
        return "redirect:/";
    }

}

package com.eni.pizzaWebsite.ihm;

import com.eni.pizzaWebsite.bll.IClientManager;
import com.eni.pizzaWebsite.bll.IOrderManager;
import com.eni.pizzaWebsite.bll.IProductManager;
import com.eni.pizzaWebsite.bo.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@SessionAttributes("order")
@Controller
public class OrderController {

    private IProductManager productManager;
    private IOrderManager orderManager;
    private IClientManager clientManager;

    public OrderController(IProductManager productManager,IOrderManager orderManager,IClientManager clientManager){
        this.productManager = productManager;
        this.orderManager = orderManager;
        this.clientManager = clientManager;
    }

    // Afficher le listing des commandes
    @GetMapping("/orders-list/{id_state}")
    public String showOrdersList(Model model, @PathVariable(value = "id_state", required = false) Long id_state) {

        if (id_state == null) {
            id_state = 0L;
        }
        List<Order> orders = orderManager.getOrdersList(id_state);
        model.addAttribute("orders", orders);

        List<Client> clients = clientManager.getClientsList();
        model.addAttribute("clients", clients);

        List<State> states = orderManager.getStatesList();
        model.addAttribute("states", states);

        Client client=new Client();
        model.addAttribute("client", client);

        State state=new State();
        model.addAttribute("state", state);

        return "orders-list";
    }

    @PostMapping("/orders-list")
    public String FilterOrdersList(@ModelAttribute("state") State state){
        System.out.println(state);
        return "redirect:orders-list/"+ state.getId_state();
    }

    @PostMapping("/create-order")
    public String createOrder(@RequestParam("id_client") Long id_client) {
        Long id_user = 1L;
        orderManager.addProductToOrder(null, id_client, 0L, 0L);
        return "redirect:/orders-list/0";
    }

    @GetMapping("/orders-edit/{id_order}")
    public String editOrder(@PathVariable("id_order") Long id_order, Model model) {

        Order selectedOrder = orderManager.getOrder(null, id_order);
        model.addAttribute("selectedOrder", selectedOrder);

        List<OrderDetail> selectedOrderDetails = orderManager.getOrderDetailsByIdOrder(id_order);
        model.addAttribute("selectedOrderDetails", selectedOrderDetails );

        List<Product> products = productManager.getProductsList();
        model.addAttribute("products", products);

        Product product=new Product();
        model.addAttribute("product", product);

        OrderDetail orderDetail=new OrderDetail();
        orderDetail.setId_order(selectedOrder.getId_order());
        model.addAttribute("orderDetail", orderDetail);


        List<ProductSize> productsSizes=orderManager.getPriceByProductSize();
        model.addAttribute("productsSizes", productsSizes);

        Prices prices=new Prices(orderManager);
        model.addAttribute("prices", prices);

        LocalDateTime delivery_datetime = LocalDateTime.now();

        return "edit-order";


    }

    @PostMapping("/add-orderDetail-to-order")
    public String addOrderDetailToOrder(@ModelAttribute OrderDetail orderDetail, BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            return "redirect:/orders-edit/" + orderDetail.getId_order();
        }
        System.out.println("orderDetail submitted: " +  orderDetail.toString());
        orderManager.addOrderDetailToOrder(orderDetail.getProduct(),orderDetail.getId_order(),(long) orderDetail.getId_size(),(long) orderDetail.getQuantity());

        return "redirect:/orders-edit/" + orderDetail.getId_order();

    }

    @PostMapping("/remove-product-from-order")
    public String removeProductFromOrder(@ModelAttribute OrderDetail orderDetail, BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            return "redirect:/orders-edit/" + orderDetail.getId_order();
        }
        System.out.println("orderDetail submitted: " +  orderDetail.toString());
        orderManager.removeOrderDetailToOrder(orderDetail.getId_order(), orderDetail.getProduct().getId_product(),(long)orderDetail.getId_size());

        return "redirect:/orders-edit/" + orderDetail.getId_order();
    }

    @PostMapping("/update-order-state/{id_order}")
    public String updateOrderState(@PathVariable("id_order") Long id_order, @RequestParam("id_state") Long id_state) {
        orderManager.updateOrderState(id_order, id_state);
        return "redirect:/orders-list/0";
    }

    @PostMapping("/update-order-delivery-date/{id_order}")
    public String updateOrderDeliveryDate(@PathVariable("id_order") Long id_order, @RequestParam("delivery_datetime") LocalDateTime delivery_datetime) {
        orderManager.updateOrderDeliveryDate(id_order, delivery_datetime);
        return "redirect:/orders-list/0";
    }




    //_________________________________________________________________________________________________________________________________________
//    @PostMapping("/cart-add/{id_product}")
//    public String addProductToCartOrOrder(@PathVariable("id_product") Long id_product,
//                                          @RequestParam("quantity") Long quantity,
//                                          @RequestParam("size") Long size,
//                                          @RequestParam(value = "id_order", required = false) Long id_order) {
//        Product product = productManager.getProductById(id_product);
//
//        if (id_order != null) {
//            orderManager.addProductToOrder(product, id_order, quantity, size);
//            return "redirect:/orders/edit/" + id_order;
//        } else {
//            orderManager.addProductToOrder(product, 1L, quantity, size);
//            return "redirect:/products-list";
//        }
//    }


//    @ModelAttribute("order")
//    public Order createOrder() {
//        return new Order();
//    }

    @GetMapping("/cart")
    public String viewCart(Model model, @ModelAttribute("orderDetail") OrderDetail orderDetail) {
        List<OrderDetail> orderDetails = orderManager.getOrderDetail(1L);

//        if (orderDetails != null) {
//            for (OrderDetail detail : orderDetails) {
//                Float priceDifference = detail.getPrice_difference();
//                Float adjustedPrice = detail.getProduct().getPrice() + priceDifference;
//                detail.getProduct().setPrice(adjustedPrice);
//            }
//            model.addAttribute("orderDetails", orderDetails);
//            Float totalPrice = orderManager.getOrderTotalPrice(1L);
//            model.addAttribute("totalPrice", totalPrice);
//        } else {
//            model.addAttribute("orderDetails", null);
//            model.addAttribute("totalPrice", 0);
//        }
        return "cart";
    }



//    @GetMapping("/orders/edit/{id_order}")
//    public String editOrder(@PathVariable("id_order") Long id_order, Model model) {
//        List<Order> orders = orderManager.getOrdersList(0L);
//        Order selectedOrder = orders.stream()
//                .filter(order -> order.getId_order().equals(id_order))
//                .findFirst()
//                .orElse(null);
//
//        if (selectedOrder != null) {
//            model.addAttribute("order", selectedOrder);
//            List<Product> products = productManager.getProductsList();
//            model.addAttribute("products", products);
//            return "edit-order-products";
//        } else {
//            return "redirect:/orders-list";
//        }
//    }



//    @GetMapping("/cart/remove/{id}")
//    public String removeProductFromCart(@PathVariable("id") Long id_product,
//                                        @RequestParam("size") Long size) {
//        orderManager.removeProductFromOrder(id_product, 1L, size);
//        return "redirect:/cart";
//    }

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

    @PostMapping("/orders-list/delete/{id_order}")
    public String deleteOrder(@PathVariable("id_order") Long id_order) {
        orderManager.clearOrderByIdOrder(id_order);
        return "redirect:/orders-list/0";
    }
//    @PostMapping("/orders-list/update-state/{id_order}")
//    public String updateOrderState(@PathVariable("id_order") Long id_order, @RequestParam("id_state") Long id_state) {
//        orderManager.updateOrderState(id_order, id_state);
//        return "redirect:/orders-list";
//    }
}

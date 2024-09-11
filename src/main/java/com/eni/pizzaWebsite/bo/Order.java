package com.eni.pizzaWebsite.bo;

import java.time.LocalDateTime;
import java.util.List;

public class Order {

    private Long id_order;
    private Client client;
    private User user;
    private Long id_state;
    private boolean is_in_delivery;
    private LocalDateTime delivery_datetime;
    private float total_price;
    private boolean is_paid;
    private List<OrderDetail> orderDetails;

    public Order() {
        super();
    }

    public Order(Long id_order, Client client, User user, Long id_state, boolean is_in_delivery, LocalDateTime delivery_datetime, float total_price, boolean is_paid, List<OrderDetail> orderDetails) {
        this.id_order = id_order;
        this.client = client;
        this.user = user;
        this.id_state = id_state;
        this.is_in_delivery = is_in_delivery;
        this.delivery_datetime = delivery_datetime;
        this.total_price = total_price;
        this.is_paid = is_paid;
        this.orderDetails = orderDetails;
    }

    public Long getId_order() {
        return id_order;
    }

    public void setId_order(Long id_order) {
        this.id_order = id_order;
    }

    public Long getId_state() {
        return id_state;
    }

    public void setId_state(Long id_state) {
        this.id_state = id_state;
    }

    public boolean isIs_in_delivery() {
        return is_in_delivery;
    }

    public void setIs_in_delivery(boolean is_in_delivery) {
        this.is_in_delivery = is_in_delivery;
    }

    public LocalDateTime getDelivery_datetime() {
        return delivery_datetime;
    }

    public void setDelivery_datetime(LocalDateTime delivery_datetime) {
        this.delivery_datetime = delivery_datetime;
    }

    public float getTotal_price() {
        return total_price;
    }

    public void setTotal_price(float total_price) {
        this.total_price = total_price;
    }

    public boolean isIs_paid() {
        return is_paid;
    }

    public void setIs_paid(boolean is_paid) {
        this.is_paid = is_paid;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

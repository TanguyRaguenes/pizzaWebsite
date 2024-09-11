package com.eni.pizzaWebsite.dao;

import com.eni.pizzaWebsite.bo.Order;
import com.eni.pizzaWebsite.bo.OrderDetail;
import com.eni.pizzaWebsite.bo.Product;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class DAOOrderMySQL implements IDAOOrder {

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public DAOOrderMySQL(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }


    @Override
    public void addProductToOrder(Product product, Long id_client, Long quantity) {
        Order order = getClientOrderId(id_client);
        if (order == null) {

            //Ajout de données dans order

            String sql = "INSERT INTO `order` (id_client,id_user,id_state,is_in_delivery,delivery_datetime,total_price,is_paid) " +
                    "VALUES (:id_client, :id_user, :id_state, :is_in_delivery, :delivery_datetime, :total_price, :is_paid)";
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();

            mapSqlParameterSource.addValue("id_client", id_client);
            mapSqlParameterSource.addValue("id_user", 0);
            mapSqlParameterSource.addValue("id_state", 1);
            mapSqlParameterSource.addValue("is_in_delivery", false);
            mapSqlParameterSource.addValue("delivery_datetime", LocalDateTime.now().plusMinutes(30));
            mapSqlParameterSource.addValue("total_price", 0);
            mapSqlParameterSource.addValue("is_paid", false);

            namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);

        }

        //Ajout de données dans order_details

//        if (getClientOrderId(id_client) != null) {

            String sql = "INSERT INTO order_details (id_order, id_product, quantity) " +
                    "VALUES (:id_order, :id_product, :quantity)";

            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();

            mapSqlParameterSource.addValue("id_order", order.getId_order());
            mapSqlParameterSource.addValue("id_product", product.getId_product());
            mapSqlParameterSource.addValue("quantity", quantity);

            namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);


//        }
    }


    @Override
    public Order getClientOrderId(Long id_client) {

        List<Order> orderList = jdbcTemplate.query("SELECT * FROM `order` WHERE id_client=? AND id_state=?", new BeanPropertyRowMapper<Order>(Order.class), id_client, 1);
        return orderList.get(0);

    }

    @Override
    public List<OrderDetail> getOrderDetail(Long id_client) {
        Order order = getClientOrderId(id_client);
        if (order != null) {

            String sql = "SELECT * FROM order_details WHERE id_order=?";

            List<OrderDetail> orderDetails = jdbcTemplate.query(sql, new BeanPropertyRowMapper<OrderDetail>(OrderDetail.class),order.getId_order());
            return orderDetails;

        }


        return null;
    }
}

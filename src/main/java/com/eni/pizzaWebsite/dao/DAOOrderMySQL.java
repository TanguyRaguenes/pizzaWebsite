package com.eni.pizzaWebsite.dao;

import com.eni.pizzaWebsite.bo.Order;
import com.eni.pizzaWebsite.bo.Product;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class DAOOrderMySQL implements IDAOOrder{

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public DAOOrderMySQL (JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }


    @Override
    public void addProductToOrder(Product product,Long idClient) {

//        if(order.getId_order()!=null && getOrderById(order.getId_order())!=null){
//            jdbcTemplate.update("update order set id_client=?,id_user=?,id_state=?,is_in_delivery=?,delivery_datetime=?,total_price=?,is_paid=? where id_order=?"
//                    ,order.getClient().getId_client(),order.getUser().getId_user(),order.getId_state(),order.isIs_in_delivery(),order.getDelivery_datetime(),order.getTotal_price(),order.isIs_paid(),order.getId_order());
//            return;
//        }


        String sql="INSERT INTO `order` (id_client,id_user,id_state,is_in_delivery,delivery_datetime,total_price,is_paid) VALUES (:id_client, :id_user, :id_state, :is_in_delivery, :delivery_datetime, :total_price, :is_paid)";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();

        mapSqlParameterSource.addValue("id_client", idClient);
        mapSqlParameterSource.addValue("id_user", 0);
//        mapSqlParameterSource.addValue("id_client", order.getClient().getId_client());
//        mapSqlParameterSource.addValue("id_user", order.getUser().getId_user());
        mapSqlParameterSource.addValue("id_state", 1);
        mapSqlParameterSource.addValue("is_in_delivery", false);
        mapSqlParameterSource.addValue("delivery_datetime", LocalDateTime.now());
        mapSqlParameterSource.addValue("total_price", 0);
        mapSqlParameterSource.addValue("is_paid", false);

        namedParameterJdbcTemplate.update(sql,mapSqlParameterSource);





    }

    @Override
    public Order getOrderById(Long id_order) {

        List<Order> orderList = jdbcTemplate.query("SELECT * FROM order WHERE id_order=?", new BeanPropertyRowMapper<Order>(Order.class),id_order );
        return orderList.get(0);

    }
}

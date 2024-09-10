package com.eni.pizzaWebsite.dao;


import com.eni.pizzaWebsite.bo.Product;
import org.springframework.context.annotation.Profile;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;


@Profile("sql")
public class DAOProductMySQL implements  IDAOProduct{


    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Override
    public List<Product> getProductsList() {


        String sql = "SELECT * FROM product";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Product>(Product.class));
        
    }

    @Override
    public void addProductToList(Product product) {


    }

    @Override
    public void deleteProductFromList(Product product) {

    }
}

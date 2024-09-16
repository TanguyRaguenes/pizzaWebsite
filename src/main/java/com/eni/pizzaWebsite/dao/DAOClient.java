package com.eni.pizzaWebsite.dao;

import com.eni.pizzaWebsite.bo.Client;
import com.eni.pizzaWebsite.bo.Product;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

public class DAOClient implements IDAOClient{

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Client> getClientsList() {

        String sql = "SELECT * FROM client";

        List<Client> clientsList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Client>(Client.class));
        return clientsList;
    }
}

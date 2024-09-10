package com.eni.pizzaWebsite.dao;


import com.eni.pizzaWebsite.bo.Product;
import org.springframework.context.annotation.Profile;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Profile("sql")
public class DAOProductMySQL implements  IDAOProduct{


    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public DAOProductMySQL(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }
    @Override
    public List<Product> getProductsList() {


        String sql = "SELECT * FROM product";

        List<Product> productList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Product>(Product.class));
        return productList;
        
    }

    @Override
    public void addProductToList(Product product) {

        String sql="INSERT INTO product (name,description,price,image_url,size) VALUES (:name, :description, :price, :image_url,:size)";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("name", product.getName());
        mapSqlParameterSource.addValue("description", product.getDescription());
        mapSqlParameterSource.addValue("price", product.getPrice());
        mapSqlParameterSource.addValue("image_url", product.getImage_url());
        mapSqlParameterSource.addValue("size", product.getSize());

        namedParameterJdbcTemplate.update(sql,mapSqlParameterSource);


    }

    @Override
    public void deleteProductFromList(Product product) {


        String sql = "DELETE FROM product WHERE id_product=:id_product";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id_product", product.getId_product());

        namedParameterJdbcTemplate.update(sql,mapSqlParameterSource);

    }
}

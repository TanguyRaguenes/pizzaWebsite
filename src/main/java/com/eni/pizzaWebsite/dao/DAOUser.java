package com.eni.pizzaWebsite.dao;

import com.eni.pizzaWebsite.bo.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DAOUser implements IDAOUser{

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public DAOUser(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<User> getUsersList() {

        String sql = "SELECT * FROM user";

        List<User> usersList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<User>(User.class));
        return usersList;
    }


    @Override
    public void addUserToList(User user) {

        if(user.getId_user()!=null && getUserById(user.getId_user())!=null){

            jdbcTemplate.update("update user set firstName=?,lastName=?,email=?,password=?,id_role=? where user_id=?",user.getFirstName(),user.getLastName(),user.getEmail(),user.getPassword(),user.getId_role(),user.getId_user() );

            return;
        }

        String sql="";

        sql="INSERT INTO user(firstName,lastName,email,password,id_role) VALUES (:firstName, :lastName, :email, :password, :id_role)";
        MapSqlParameterSource mapSqlParameterSourceUser = new MapSqlParameterSource();
        mapSqlParameterSourceUser.addValue("firstName", user.getFirstName());
        mapSqlParameterSourceUser.addValue("lastName", user.getLastName());
        mapSqlParameterSourceUser.addValue("email", user.getEmail());
        mapSqlParameterSourceUser.addValue("password", PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(user.getPassword()));
        mapSqlParameterSourceUser.addValue("id_role", user.getId_role());

        namedParameterJdbcTemplate.update(sql,mapSqlParameterSourceUser);


    }

    @Override
    public void deleteUserFromList(Long id_user) {

        String sql="";

        sql = "DELETE FROM user WHERE id_user=:id_user";
        MapSqlParameterSource mapSqlParameterSourceUser = new MapSqlParameterSource();
        mapSqlParameterSourceUser.addValue("id_user", id_user);

        namedParameterJdbcTemplate.update(sql, mapSqlParameterSourceUser);

        sql = "DELETE FROM user_role WHERE id_user=:id_user";
        MapSqlParameterSource mapSqlParameterSourceUserRole = new MapSqlParameterSource();
        mapSqlParameterSourceUserRole.addValue("id_user", id_user);

        namedParameterJdbcTemplate.update(sql, mapSqlParameterSourceUserRole);
    }

    @Override
    public User getUserById(Long id_user) {

        List<User> usersList = jdbcTemplate.query("SELECT * FROM user WHERE id_user=?", new BeanPropertyRowMapper<User>(User.class),id_user );
        return usersList.get(0);

    }


}

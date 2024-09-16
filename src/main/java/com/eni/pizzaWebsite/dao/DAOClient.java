package com.eni.pizzaWebsite.dao;

import com.eni.pizzaWebsite.bo.Client;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

public class DAOClient implements IDAOClient{

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public DAOClient(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Client> getClientsList() {

        String sql = "SELECT * FROM client";

        List<Client> clientsList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Client>(Client.class));
        return clientsList;
    }


    @Override
    public void addClientToList(Client client) {

        if(client.getId_client()!=null && getClientById(client.getId_client())!=null){
            jdbcTemplate.update("update client set firstName=?,lastName=?,street=?,postalCode=? where city=?",client.getFirstName(),client.getLastName(),client.getStreet(),client.getPostalCode(),client.getCity() );
            return;
        }


        String sql="INSERT INTO client(firstName,lastName,street,postalCode,city) VALUES (:firstName, :lastName, :street, :postalCode, :city)";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("firstName", client.getFirstName());
        mapSqlParameterSource.addValue("lastName", client.getLastName());
        mapSqlParameterSource.addValue("street", client.getStreet());
        mapSqlParameterSource.addValue("postalCode", client.getPostalCode());
        mapSqlParameterSource.addValue("city", client.getCity());

        namedParameterJdbcTemplate.update(sql,mapSqlParameterSource);


    }

    @Override
    public void deleteClientFromList(Long id_client) {

        String sql = "DELETE FROM client WHERE id_client=:id_client";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id_client", id_client);

        namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
    }

    @Override
    public Client getClientById(Long id_client) {

        List<Client> ClientsList = jdbcTemplate.query("SELECT * FROM client WHERE id_client=?", new BeanPropertyRowMapper<Client>(Client.class),id_client );
        return ClientsList.get(0);

    }
}

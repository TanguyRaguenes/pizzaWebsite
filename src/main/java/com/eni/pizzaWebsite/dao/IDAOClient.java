package com.eni.pizzaWebsite.dao;

import com.eni.pizzaWebsite.bo.Client;

import java.util.List;

public interface IDAOClient {
    public abstract List<Client> getClientsList();
}

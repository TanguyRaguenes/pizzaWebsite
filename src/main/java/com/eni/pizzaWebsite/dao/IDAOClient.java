package com.eni.pizzaWebsite.dao;

import com.eni.pizzaWebsite.bo.Client;
import com.eni.pizzaWebsite.bo.Product;

import java.util.List;

public interface IDAOClient {
    public abstract List<Client> getClientsList();
    public abstract Client getClientById(Long id_client);
    public abstract void addClientToList(Client client);
    public abstract void deleteClientFromList(Long id_client);
}

package com.eni.pizzaWebsite.bll;

import com.eni.pizzaWebsite.bo.Client;

import java.util.List;

public interface IClientManager {

    public abstract List<Client> getClientsList();
    public abstract Client getClientById(Long id_client);
    public abstract void addClientToList(Client client);
    public abstract void deleteClientFromList(Long id_client);
}

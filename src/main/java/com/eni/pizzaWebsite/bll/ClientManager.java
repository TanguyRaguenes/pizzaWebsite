package com.eni.pizzaWebsite.bll;

import com.eni.pizzaWebsite.bo.Client;
import com.eni.pizzaWebsite.dao.IDAOClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClientManager implements IClientManager {

    private IDAOClient daoClient;

    public ClientManager(IDAOClient daoClient) {
        super();
        this.daoClient = daoClient;

    }

    @Override
    public List<Client> getClientsList() {
        return daoClient.getClientsList();
    }

    @Override
    public Client getClientById(Long id_client) {
        return daoClient.getClientById(id_client);
    }

    @Override
    public void addClientToList(Client client) {
        daoClient.addClientToList(client);
    }

    @Override
    public void deleteClientFromList(Long id_client) {
        daoClient.deleteClientFromList(id_client);
    }
}

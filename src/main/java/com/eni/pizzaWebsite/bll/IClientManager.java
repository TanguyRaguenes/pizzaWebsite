package com.eni.pizzaWebsite.bll;

import com.eni.pizzaWebsite.bo.Client;

import java.util.List;

public interface IClientManager {

    public abstract List<Client> getClientsList();
}

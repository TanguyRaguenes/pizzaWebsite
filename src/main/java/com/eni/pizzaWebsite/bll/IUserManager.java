package com.eni.pizzaWebsite.bll;

import com.eni.pizzaWebsite.bo.Client;
import com.eni.pizzaWebsite.bo.User;

import java.util.List;

public interface IUserManager {

    public abstract List<User> getUsersList();
    public abstract User getUserById(Long id_user);
    public abstract void addUserToList(User user,String user_role);
    public abstract void deleteUserFromList(Long id_user);
}

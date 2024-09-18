package com.eni.pizzaWebsite.bll;

import com.eni.pizzaWebsite.bo.User;
import com.eni.pizzaWebsite.dao.IDAOUser;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserManager implements IUserManager{

    private IDAOUser userDao;

    public UserManager(IDAOUser userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<User> getUsersList() {
        return userDao.getUsersList();
    }

    @Override
    public User getUserById(Long id_user) {
        return userDao.getUserById(id_user);
    }

    @Override
    public void addUserToList(User user) {
        userDao.addUserToList(user);
    }

    @Override
    public void deleteUserFromList(Long id_user) {
        userDao.deleteUserFromList(id_user);
    }
}

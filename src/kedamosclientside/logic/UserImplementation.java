/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kedamosclientside.logic;

import java.util.Collection;
import java.util.Set;
import javax.ws.rs.core.GenericType;
import kedamosclientside.entities.Client;
import kedamosclientside.entities.EventManager;
import kedamosclientside.entities.User;
import kedamosclientside.restful.UserREST;

/**
 *
 * @author Steven Arce
 */
public class UserImplementation implements UserInterface {

    private UserREST webClient;

    public UserImplementation() {
        webClient = new UserREST();
    }
    
    @Override
    public void createUser(User user) {
        webClient.create(user);
    }

    @Override
    public void editUser(User user) {
        webClient.edit(user, user.getUser_id());
    }

    @Override
    public void removeUser(User user) {
        webClient.remove(user.getUser_id());
    }

    @Override
    public User findUser(User user) {
        User userBean;
        userBean = webClient.find(new GenericType<User>(){}, user.getUser_id());
        return userBean;
    }

    @Override
    public Collection<User> findAllUser() {
        Set<User> users;
        users = webClient.findAll(new GenericType<Set<User>>(){});
        return users;
    }

    @Override
    public Collection<User> LoginValidation(User user) {
        Set<User> users;
        users = webClient.validateLogin(new GenericType<Set<User>>(){}, user.getUsername(), user.getPassword());
        return users;
    }
}

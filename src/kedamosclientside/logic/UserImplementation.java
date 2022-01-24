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
        webClient.edit(user, user.getUser_id().toString());
    }

    @Override
    public void removeUser(User user) {
        webClient.remove(user.getUser_id().toString());
    }

    @Override
    public User findUser(User user) {
        User userBean;
        userBean = webClient.find(new GenericType<User>() {
        }, user.getUser_id().toString());
        return userBean;
    }

    @Override
    public Collection<User> findAllUser() {

        Set<User> users = null;
        users = webClient.findAll(new GenericType<Set<User>>() {
        });
        return users;

    }

    @Override
    public User resetPassword(User user) {

        User userBean;
        userBean = webClient.resetPassword(new GenericType<User>() {},
                user.getEmail());
        /*
        if (userBean instanceof Client) {
            userBean = (Client) userBean;
        }
        if (userBean instanceof EventManager) {
            userBean = (EventManager) userBean;
        }
        */
        return userBean;

    }

    @Override
    public User isEmailExisting(User user) {

        User userBean;
        userBean = webClient.isEmailExisting(new GenericType<User>() {
        }, user.getEmail());
        return userBean;
    }

    @Override
    public User isUsernameExisting(User user) {
        User userBean;
        userBean = webClient.isUsernameExisting(new GenericType<User>() {
        }, user.getUsername());
        return userBean;
    }

    @Override
    public User userLoginValidation(User user) {
        User userBean;
        userBean = webClient.userLoginValidation(new GenericType<User>(){},
                user.getUsername(), user.getPassword());
        return userBean;
    }

}

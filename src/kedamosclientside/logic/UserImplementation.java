/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kedamosclientside.logic;

import java.util.Collection;
import java.util.Set;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.GenericType;
import kedamosclientside.entities.User;
import kedamosclientside.exceptions.PasswordIncorrect;
import kedamosclientside.exceptions.ServerDown;
import kedamosclientside.exceptions.UsernameDoesNotExist;
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
        userBean = webClient.find(new GenericType<User>() {
        }, user.getUser_id());
        return userBean;
    }

    @Override
    public Collection<User> findAllUser() {
        Set<User> users;
        users = webClient.findAll(new GenericType<Set<User>>() {
        });
        return users;
    }

    @Override
    public Collection<User> LoginValidation(User user) throws UsernameDoesNotExist, 
            PasswordIncorrect, ServerDown {
        Set<User> users = null;
        try {
            users = webClient.validateLogin(new GenericType<Set<User>>() {
            }, user.getUsername(), user.getPassword());
        } catch (NotFoundException ex) {
            throw new UsernameDoesNotExist("Username dont exists");
        } catch (NotAuthorizedException ex) {
            throw new PasswordIncorrect("Incorrect Password");
        } catch (Exception ex) {
            throw new ServerDown("server down, try again later");
        }
        return users;
    }
}

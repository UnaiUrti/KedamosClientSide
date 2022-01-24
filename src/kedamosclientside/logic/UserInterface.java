/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kedamosclientside.logic;

import java.util.Collection;
import java.util.List;
import kedamosclientside.entities.User;

/**
 *
 * @author Steven Arce
 */
public interface UserInterface {
    /**
     * 
     * @param user 
     */
    public void createUser(User user);
    /**
     * 
     * @param user 
     */
    public void editUser(User user);
    /**
     * 
     * @param user 
     */
    public void removeUser(User user);
    /**
     * 
     * @param user
     * @return 
     */
    public User findUser(User user);
    /**
     * 
     * @return 
     */
    public Collection<User> findAllUser();
    /**
     * 
     * @param <T>
     * @param user
     * @return 
     */
    public <T> T resetPassword(User user);
    /**
     * 
     * @param user
     * @return 
     */
    public User isEmailExisting(User user);
    /**
     * 
     * @param user
     * @return 
     */
    public User isUsernameExisting(User user);
    /**
     * 
     * @param user
     * @return 
     */
    public User userLoginValidation(User user);
    
}

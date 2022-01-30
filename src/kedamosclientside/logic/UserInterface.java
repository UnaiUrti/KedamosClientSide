/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kedamosclientside.logic;

import java.util.Collection;
import kedamosclientside.entities.User;
import kedamosclientside.exceptions.PasswordIncorrect;
import kedamosclientside.exceptions.ServerDown;
import kedamosclientside.exceptions.UsernameDoesNotExist;

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
     * @param user
     * @return 
     * @throws kedamosclientside.exceptions.UsernameDoesNotExist 
     * @throws kedamosclientside.exceptions.PasswordIncorrect 
     * @throws kedamosclientside.exceptions.ServerDown 
     */
    public Collection<User> LoginValidation(User user) throws UsernameDoesNotExist,
            PasswordIncorrect, ServerDown;
}

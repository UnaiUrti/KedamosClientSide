/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kedamosclientside.logic;

import java.util.List;
import kedamosclientside.entities.UserBean;

/**
 *
 * @author Steven Arce
 */
public interface UserInterface {
    /**
     * 
     * @param user 
     */
    public void createUser(UserBean user);
    /**
     * 
     * @param user 
     */
    public void editUser(UserBean user);
    /**
     * 
     * @param user 
     */
    public void removeUser(UserBean user);
    /**
     * 
     * @param user
     * @return 
     */
    public UserBean findUser(UserBean user);
    /**
     * 
     * @return 
     */
    public List<UserBean> findAllUser();
 
    
}

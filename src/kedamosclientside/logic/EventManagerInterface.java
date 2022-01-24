/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kedamosclientside.logic;

import java.util.Collection;
import kedamosclientside.entities.EventManager;

/**
 *
 * @author Steven Arce
 */
public interface EventManagerInterface {

    public void createEventManager(EventManager eventManager);

    public void editEventManager(EventManager eventManager);

    public void removeEventManager(EventManager eventManager);

    public EventManager findEventManager(EventManager eventManager);

    public Collection<EventManager> findAll();
    
    public EventManager getEventManagerByUsername(EventManager eventManager);
    
    public EventManager isUsernameExisting(EventManager eventManager);
    
    public EventManager isEmailExisting(EventManager eventManager);
    
    public EventManager eventManagerLoginValidation(EventManager eventManager);

}

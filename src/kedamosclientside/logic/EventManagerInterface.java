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
    
    public EventManager getEventManagerByUsername(EventManager eventManager, String username);

    public void edit(EventManager eventManager, String id);

    public EventManager find(EventManager eventManager, String id);

    public void create(EventManager eventManager);

    public Collection<EventManager> findAll();

    public void remove(String id);

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kedamosclientside.logic;

import java.util.Collection;
import java.util.Set;
import javax.ws.rs.core.GenericType;
import kedamosclientside.entities.EventManager;
import kedamosclientside.restful.EventManagerREST;

/**
 *
 * @author Steven Arce
 */
public class EventManagerImplementation implements EventManagerInterface{

    private EventManagerREST webClient;

    public EventManagerImplementation() {
        webClient = new EventManagerREST();
    }

    
    @Override
    public EventManager getEventManagerByUsername(EventManager eventManager, String username) {
        EventManager eventManagerBean;
        eventManagerBean = webClient.getEventManagerByUsername(new GenericType<EventManager>(){}, username);
        return eventManagerBean;
        
    }

    @Override
    public void edit(EventManager eventManager, String id) {
       webClient.edit(eventManager, id);
    }

    @Override
    public EventManager find(EventManager eventManager, String id) {
        EventManager eventManagerBean;
        eventManagerBean = webClient.find(new GenericType<EventManager>(){}, id);
        return eventManagerBean;
    }

    @Override
    public void create(EventManager eventManager) {
        webClient.create(eventManager);
    }

    @Override
    public Collection<EventManager> findAll() {
        Set<EventManager> eventManagers;
        eventManagers = webClient.findAll(new GenericType<Set<EventManager>>(){});
        return eventManagers;
    }

    @Override
    public void remove(String id) {
        webClient.remove(id);
    }
    
}

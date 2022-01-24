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
public class EventManagerImplementation implements EventManagerInterface {

    private EventManagerREST webClient;

    public EventManagerImplementation() {
        webClient = new EventManagerREST();
    }

    @Override
    public void createEventManager(EventManager eventManager) {
        webClient.create(eventManager);
    }

    @Override
    public void editEventManager(EventManager eventManager) {
        webClient.edit(eventManager, eventManager.getUser_id());
    }

    @Override
    public void removeEventManager(EventManager eventManager) {
        webClient.remove(eventManager.getUser_id());
    }

    @Override
    public EventManager findEventManager(EventManager eventManager) {
        EventManager eventManagerBean;
        eventManagerBean = webClient.find(new GenericType<EventManager>() {
        }, eventManager.getUser_id());
        return eventManagerBean;
    }

    @Override
    public Collection<EventManager> findAll() {
        Set<EventManager> eventManagers;
        eventManagers = webClient.findAll(new GenericType<Set<EventManager>>() {
        });
        return eventManagers;
    }

    @Override
    public EventManager getEventManagerByUsername(EventManager eventManager) {
        EventManager eventManagerBean;
        eventManagerBean = webClient.getEventManagerByUsername(new GenericType<EventManager>() {
        }, eventManager.getUsername());
        return eventManagerBean;
    }

    @Override
    public EventManager isUsernameExisting(EventManager eventManager) {
        EventManager eventManagerBean;
        eventManagerBean = webClient.isUsernameExisting(new GenericType<EventManager>() {
        }, eventManager.getUsername());
        return eventManagerBean;
    }

    @Override
    public EventManager isEmailExisting(EventManager eventManager) {
        EventManager eventManagerBean;
        eventManagerBean = webClient.isEmailExisting(new GenericType<EventManager>() {
        }, eventManager.getEmail());
        return eventManagerBean;
    }

    @Override
    public EventManager eventManagerLoginValidation(EventManager eventManager) {
        EventManager eventManagerBean;
        eventManagerBean = webClient.eventManagerLoginValidation(new GenericType<EventManager>(){}, eventManager.getUsername(), eventManager.getPassword());
        return eventManagerBean;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kedamosclientside.logic;

import java.net.ConnectException;
import java.util.Collection;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;
import kedamosclientside.entities.Event;
import kedamosclientside.restful.EventClientREST;

/**
 *
 * @author Adrian Franco Implementacion de la interfaz
 */
public class EventImplementation implements EventInterface {

    public EventImplementation() {
        eventREST = new EventClientREST();
    }
    private EventClientREST eventREST;

    /**
     *
     * @param event
     * @throws ConnectException
     */
    @Override
    public void createEvent(Event event) throws ConnectException {
        try {
            eventREST.create(event);
        } catch (Exception e) {
            throw new ConnectException("Server down");
        }
    }

    @Override
    public void editEvent(Event event) throws ConnectException {
        try {
            eventREST.edit(event, String.valueOf(event.getEvent_id()));
        } catch (Exception e) {
            throw new ConnectException(e.getMessage());
        }
    }

    @Override
    public void removeEvent(Event event) throws ConnectException {
        try{
        eventREST.remove(event.getEvent_id().toString());
        }catch (Exception e){
            throw new ConnectException("Server down");
        }
    }

    @Override
    public void searchEvent(Event event) throws ConnectException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<Event> getEvents() throws ConnectException {

        Collection<Event> events;
        try {
            events = eventREST.findAll(new GenericType<Collection<Event>>() {
            });
        } catch (Exception e) {
            throw new ConnectException("Server down");
        }
        return events;
    }
}

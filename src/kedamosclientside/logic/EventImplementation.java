/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kedamosclientside.logic;

import java.util.Collection;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;
import kedamosclientside.entities.Event;
import kedamosclientside.exceptions.ClientLogicException;
import kedamosclientside.restful.EventClientREST;

/**
 *
 * @author Adrian Franco
 */
public class EventImplementation implements EventInterface {

    public EventImplementation() {
        eventREST = new EventClientREST();
    }
    private EventClientREST eventREST;

    @Override
    public void createEvent(Event event) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editEvent(Event event) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeEvent(Event event) throws Exception {
       eventREST.remove(event.getEvent_id().toString());
    }

    @Override
    public void searchEvent(Event event) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<Event> getEvents() throws ClientLogicException {

        Collection<Event> events = null;
        try {
            events =eventREST.findAll(new GenericType<Collection<Event>>() {

            });
        } catch (ClientErrorException e) {
                throw new ClientLogicException(e.getMessage());
        }
        return events;
    }
}

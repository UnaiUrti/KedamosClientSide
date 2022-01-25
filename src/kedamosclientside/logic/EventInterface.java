/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kedamosclientside.logic;

import java.util.Collection;
import kedamosclientside.entities.Event;
import kedamosclientside.exceptions.ClientLogicException;



/**
 *
 * @author Adrian Franco
 */
public interface EventInterface {
    
    public void createEvent(Event event) throws Exception;
    
    public void editEvent (Event event) throws Exception;
    
    public void removeEvent (Event event) throws Exception;
    
    public void searchEvent (Event event) throws Exception;
    
    public Collection <Event> getEvents() throws ClientLogicException;
            
}

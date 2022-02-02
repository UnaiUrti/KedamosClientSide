/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kedamosclientside.logic;

import java.net.ConnectException;
import java.util.Collection;
import kedamosclientside.entities.Event;




/**
 *Interfaz con las excepciones añadidas
 * @author Adrian Franco
 */
public interface EventInterface {
    
    public void createEvent(Event event) throws ConnectException;
    
    public void editEvent (Event event) throws ConnectException;
    
    public void removeEvent (Event event) throws ConnectException;
    
    public void searchEvent (Event event) throws ConnectException;
    
    public Collection <Event> getEvents() throws ConnectException;
            
}

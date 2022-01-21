/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kedamosclientside.logic;

/**
 *
 * @author Adrian Franco
 */
public class EventFactory {
    
    public static EventInterface getEvent(){
        EventInterface event = new EventImplementation();
        return event;
    }
}

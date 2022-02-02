/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kedamosclientside.exceptions;

/**
 *
 * @author Adrian Franco
 * Excepcion para problemas con min y max participants
 */
public class EventParticipantsException extends Exception{
    
    public EventParticipantsException(String message){
        super(message);
    }
}

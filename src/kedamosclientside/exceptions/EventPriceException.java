/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kedamosclientside.exceptions;

/**
 *
 * @author Adrian Franco
 * Excepcion para campos del precio no validos
 */
public class EventPriceException extends Exception{
    
        public EventPriceException(String message){
        super(message);
    }
}

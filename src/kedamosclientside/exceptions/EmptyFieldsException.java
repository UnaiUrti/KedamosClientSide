/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kedamosclientside.exceptions;

/**
 *
 * @author Adrian Franco, Unai Urtiaga
 * Excepcion para campos vacios
 */
public class EmptyFieldsException extends Exception {

    public EmptyFieldsException(String msg) {
        super(msg);
    }
    
}

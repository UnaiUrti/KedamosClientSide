/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kedamosclientside.exceptions;

/**
 *
 * @author Adrian Franco
 * Excepcion para cuando se superan 255caracteres
 */
public class MaxCharacterException extends Exception{

    public MaxCharacterException(String message) {
        super(message);
    }
    
}

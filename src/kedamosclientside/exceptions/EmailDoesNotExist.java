/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kedamosclientside.exceptions;

/**
 *
 * @author Steven Arce
 */
public class EmailDoesNotExist extends Exception{
    
    public EmailDoesNotExist(String msg) {
        super(msg);
    }
    
}
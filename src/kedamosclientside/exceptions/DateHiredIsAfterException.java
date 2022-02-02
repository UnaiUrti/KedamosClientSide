/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kedamosclientside.exceptions;

/**
 *
 * @author Irkus de la fuente
 */
public class DateHiredIsAfterException extends Exception {
    public DateHiredIsAfterException(String msg){
        super(msg);
    }
}

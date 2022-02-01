package kedamosclientside.exceptions;

/**
 * Excepcion lanzada cuando el email no existe.
 *
 * @author Steven Arce
 */
public class EmailDoesNotExist extends Exception {

    public EmailDoesNotExist(String msg) {
        super(msg);
    }

}

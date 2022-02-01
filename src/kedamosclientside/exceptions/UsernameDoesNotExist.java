package kedamosclientside.exceptions;

/**
 * Excepcion lanzada cuando el nombre de usuario no existe.
 *
 * @author Steven Arce
 */
public class UsernameDoesNotExist extends Exception {

    public UsernameDoesNotExist(String msg) {
        super(msg);
    }

}

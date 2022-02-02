package kedamosclientside.exceptions;

/**
 * Excepcion lanzada cuando la contraseña introducida no es correcta.
 *
 * @author Steven Arce
 */
public class PasswordIncorrect extends Exception {

    public PasswordIncorrect(String msg) {
        super(msg);
    }

}

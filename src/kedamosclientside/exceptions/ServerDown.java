package kedamosclientside.exceptions;

/**
 * Excepcion lanzada cuando el servidor esta fuera de servicio.
 *
 * @author Steven Arce
 */
public class ServerDown extends Exception {

    public ServerDown(String msg) {
        super(msg);
    }

}

package kedamosclientside.logic;

/**
 * Esta clase es una factoria para la interfaz {@link ClientInterface}.
 *
 * @author Steven Arce
 */
public class ClientFactory {

    /**
     * Este metodo devuelve un objeto que implementa la interfaz.
     *
     * @return Un objeto que implementa la interfaz.
     */
    public static ClientInterface getClientImplementation() {

        ClientInterface ci = new ClientImplementation();
        return ci;

    }

}

package kedamosclientside.logic;

/**
 * Esta clase es una factoria para la interfaz {@link EventManagerInterface}.
 *
 * @author Steven Arce
 */
public class EventManagerFactory {

    /**
     * Este metodo devuelve un objeto que implementa la interfaz.
     * 
     * @return Un objeto que implementa la interfaz.
     */
    public static EventManagerInterface getEventManagerImplementation() {

        EventManagerInterface emi = new EventManagerImplementation();
        return emi;

    }

}

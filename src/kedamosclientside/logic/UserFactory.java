package kedamosclientside.logic;

/**
 * Esta clase es una factoria para la interfaz {@link UserInterface}.
 *
 * @author Steven Arce
 */
public class UserFactory {

    /**
     * Este metodo devuelve un objeto que implementa la interfaz.
     *
     * @return Un objeto que implementa la interfaz.
     */
    public static UserInterface getUserImplementation() {

        UserInterface um = new UserImplementation();
        return um;

    }

}

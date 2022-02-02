package kedamosclientside.entities;

/**
 * Este clase pretende instanciar un unico objeto ClientHolder donde se guarda
 * la informacion de un cliente.
 *
 * @author Steven Arce
 */
public class ClientHolder {

    private final static ClientHolder INSTANCE = new ClientHolder();
    private Client client;

    private ClientHolder() {
    }

    public static ClientHolder getInstance() {
        return INSTANCE;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

}

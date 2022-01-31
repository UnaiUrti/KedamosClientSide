/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kedamosclientside.entities;

/**
 *
 * @author Steven Arce
 */
public class ClientHolder {

    private final static ClientHolder INSTANCE = new ClientHolder();
    private Client client;

    private ClientHolder() {}

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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kedamosclientside.logic;

import java.util.Collection;
import kedamosclientside.entities.Client;

/**
 *
 * @author Steven Arce
 */
public interface ClientInterface {

    public Client getClientByUsername(Client client, String username);

    public Client validatePassword(Client client, String username, String passwd);

    public void edit(Client client, String id);

    public Client find(Client client, String id);

    public void create(Client client);

    public Collection<Client> findAll();

    public void remove(String id);

    public void changePassword(Client client, String id);

}

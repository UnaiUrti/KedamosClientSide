/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kedamosclientside.logic;

import java.util.Collection;
import kedamosclientside.entities.Client;
import kedamosclientside.exceptions.EmailDoesNotExist;
import kedamosclientside.exceptions.PasswordIncorrect;

/**
 *
 * @author Steven Arce
 */
public interface ClientInterface {

    public void createClient(Client client);

    public void editClient(Client client);

    public void removeClient(Client client);

    public Client findClient(Client client);

    public Collection<Client> findAllClient();
 
    public Client resetPassword(Client client) throws EmailDoesNotExist;
 
    public void changePassword(Client client);
 
    public Client validatePassword(Client client) throws PasswordIncorrect;
 
    public Client getClientByUsername(Client client);

    public Client isUsernameExisting(Client client);
 
    public Client isEmailExisting(Client client);
 
    public Client clientLoginValidation(Client client);
    

}

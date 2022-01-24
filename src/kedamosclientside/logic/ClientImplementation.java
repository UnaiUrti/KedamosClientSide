/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kedamosclientside.logic;

import java.util.Collection;
import java.util.Set;
import javax.ws.rs.core.GenericType;
import kedamosclientside.entities.Client;
import kedamosclientside.restful.ClientREST;

/**
 *
 * @author 2dam
 */
public class ClientImplementation implements ClientInterface {

    private ClientREST webClient;

    public ClientImplementation() {
        webClient = new ClientREST();
    }

    @Override
    public void createClient(Client client) {
        webClient.create(client);
    }

    @Override
    public void editClient(Client client) {
        webClient.edit(client, client.getUser_id());
    }

    @Override
    public void removeClient(Client client) {
        webClient.remove(client.getUser_id());
    }

    @Override
    public Client findClient(Client client) {
        Client clientBean = null;
        webClient.find(new GenericType<Client>() {
        }, client.getUser_id());
        return clientBean;
    }

    @Override
    public Collection<Client> findAllClient() {
        Set<Client> clients;
        clients = webClient.findAll(new GenericType<Set<Client>>() {
        });
        return clients;
    }

    @Override
    public Client resetPassword(Client client) {
        Client clientBean;
        clientBean = webClient.resetPassword(new GenericType<Client>() {
        }, client.getEmail());
        return clientBean;
    }

    @Override
    public void changePassword(Client client) {
        webClient.changePassword(client, client.getUser_id());
    }

    @Override
    public Client validatePassword(Client client) {
        Client clientBean;
        clientBean = webClient.validatePassword(new GenericType<Client>() {
        }, client.getUsername(), client.getPassword());
        return clientBean;
    }

    @Override
    public Client getClientByUsername(Client client) {
        Client clientBean;
        clientBean = webClient.getClientByUsername(new GenericType<Client>() {
        }, client.getUsername());
        return clientBean;
    }

    @Override
    public Client isUsernameExisting(Client client) {
        Client clientBean;
        clientBean = webClient.isUsernameExisting(new GenericType<Client>() {
        }, client.getUsername());
        return clientBean;
    }

    @Override
    public Client isEmailExisting(Client client) {
        Client clientBean;
        clientBean = webClient.isEmailExisting(new GenericType<Client>() {
        }, client.getEmail());
        return clientBean;
    }

    @Override
    public Client clientLoginValidation(Client client) {
        Client clientBean;
        clientBean = webClient.clientLoginValidation(new GenericType<Client>() {
        }, client.getUsername(), client.getPassword());
        return clientBean;
    }

}

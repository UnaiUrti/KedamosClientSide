/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kedamosclientside.logic;

import java.util.Collection;
import java.util.Set;
import javax.ws.rs.core.GenericEntity;
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
    public Client getClientByUsername(Client client, String username) {
        Client clientBean;
        clientBean = webClient.getClientByUsername(new GenericType<Client>() {
        }, username);
        return clientBean;
    }

    @Override
    public Client validatePassword(Client client, String username, String passwd) {
        Client clientBean;
        clientBean = webClient.validatePassword(new GenericType<Client>() {
        }, username, passwd);
        return clientBean;
    }

    @Override
    public void edit(Client client, String id) {
        webClient.edit(new GenericType<Client>() {
        }, id);
    }

    @Override
    public Client find(Client client, String id) {
        Client clientBean;
        clientBean = webClient.find(new GenericType<Client>() {
        }, id);
        return clientBean;
    }

    @Override
    public void create(Client client) {
        webClient.create(client);
    }

    @Override
    public Collection<Client> findAll() {
        Set<Client> clients = null;
        clients = webClient.findAll(new GenericType<Set<Client>>(){});
        return clients;
    }

    @Override
    public void remove(String id) {
        webClient.remove(id);
    }

    @Override
    public void changePassword(Client client, String id) {
        webClient.changePassword(new GenericType<Client>(){}, id);
    }

}

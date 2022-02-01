/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kedamosclientside.logic;

import java.net.ConnectException;
import java.util.Collection;
import java.util.List;
import javax.ws.rs.core.GenericType;
import kedamosclientside.entities.PersonalResource;
import kedamosclientside.restful.PersonalResourceClientRest;

/**
 *Esta es la implementacion de los personal resources
 * @author Irkus de la Fuente
 */

public class PersonalResourceImplementation implements PersonalResourceInterface {

    private PersonalResourceClientRest webClient;

    public PersonalResourceImplementation() {
        webClient = new PersonalResourceClientRest();
    }
/**
 * Llamada al metodo del restful para obtener los personales de un evento
 * @param event_id
 * @return
 * @throws ConnectException 
 */
    @Override
    public Collection<PersonalResource> getPersonalByEvent(Long event_id) throws ConnectException {

        List<PersonalResource> personals;
        try {
            personals = webClient.getPersonalByEvent(new GenericType<List<PersonalResource>>() {
            }, event_id);
        } catch (Exception ex) {
            throw new ConnectException("Server down pls check it");

        }
        return personals;

    }
/**
 * Llamada al metodo del restful que crea personales
 * @param per
 * @throws ConnectException 
 */
    @Override
    public void createPersonal(PersonalResource per) throws ConnectException {
        try {
            webClient.create(per);
        } catch (Exception ex) {
            throw new ConnectException("Server down pls check it");
        }
    }
/**
 * Llamada al metodo del restful que modifica personales
 * @param per
 * @param id
 * @throws ConnectException 
 */
    @Override
    public void updatePersonal(PersonalResource per, Long id) throws ConnectException {
        try {
            webClient.edit(per, id);
        } catch (Exception ex) {
            throw new ConnectException("Server down pls check it");

        }
    }
/**
 * Llamada al metodo del restful que borra personales
 * @param id
 * @throws ConnectException 
 */
    @Override
    public void deletePersonal(Long id) throws ConnectException {
        try {
            webClient.remove(id);
        } catch (Exception ex) {
            throw new ConnectException("Server down pls check it");

        }
    }

}

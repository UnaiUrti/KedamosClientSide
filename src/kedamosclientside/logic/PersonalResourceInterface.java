/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kedamosclientside.logic;

import java.net.ConnectException;
import java.util.Collection;


import kedamosclientside.entities.PersonalResource;



/**
 *Esta es la interfaz de los personal resources
 * @author Irkus de la Fuente
 */
public interface PersonalResourceInterface {
    public Collection<PersonalResource> getPersonalByEvent(Long event_id)throws ConnectException;
    public void createPersonal (PersonalResource per) throws ConnectException;
    public void updatePersonal(PersonalResource per,Long id)throws ConnectException;
    public void deletePersonal(Long id)throws ConnectException;
}

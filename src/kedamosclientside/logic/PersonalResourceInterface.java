/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kedamosclientside.logic;

import java.util.Collection;


import kedamosclientside.entities.PersonalResource;



/**
 *
 * @author Irkus de la Fuente
 */
public interface PersonalResourceInterface {
    public Collection<PersonalResource> getPersonalByEvent(String event_id);
    public void createPersonal (PersonalResource per);
    public void updatePersonal(PersonalResource per,Long id);
    public void deletePersonal(Long id);
}

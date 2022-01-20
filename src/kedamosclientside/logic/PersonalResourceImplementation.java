/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kedamosclientside.logic;

import java.util.Collection;
import java.util.List;
import javax.ws.rs.core.GenericType;
import kedamosclientside.entities.PersonalResource;
import kedamosclientside.restful.PersonalResourceClientRest;


/**
 *
 * @author Irkus de la Fuente
 */
public class PersonalResourceImplementation implements PersonalResourceInterface {
    private PersonalResourceClientRest webClient;
    public PersonalResourceImplementation(){
        webClient=new PersonalResourceClientRest();
    }
    @Override
    public Collection<PersonalResource> getPersonalByEvent(String event_id) {
         List<PersonalResource> personals =null;
        personals = webClient.getPersonalByEvent(new GenericType<List<PersonalResource>>() {},event_id);
        
        return personals;
       
    }

    @Override
    public void createPersonal(PersonalResource per) {
        webClient.create(per);
    }

    @Override
    public void updatePersonal(PersonalResource per,Long id) {
            webClient.edit(per, id);
    }

    @Override
    public void deletePersonal(Long id) {
        webClient.remove(id);
    }

  
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kedamosclientside.entities;

import java.io.Serializable;
import javax.persistence.Embeddable;

/**
 * Clase que contiene las IDs de la entidad Revise
 * @author UnaiUrtiaga
 */
@Embeddable
public class ReviseId implements Serializable{
    
    /**
     * Id del usuario que ha revisado el evento
     */
    private Long user_id;
    /**
     * Id del evento que ha sido revisado
     */
    private Long event_id;

    /**
     * Get del Id del usuario que ha revisado el evento
     * @return Devuelve el id del usuario
     */
    public Long getUser_id() {
        return user_id;
    }

    /**
     * Set del Id del usuario que ha revisado el evento
     * @param user_id 
     */
    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    /**
     * Get del evento que ha sido revisado
     * @return Devuelve la id del evento
     */
    public Long getEvent_id() {
        return event_id;
    }

    /**
     * Set del evento que ha sido revisado
     * @param event_id 
     */
    public void setEvent_id(Long event_id) {
        this.event_id = event_id;
    }
    
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kedamosclientside.entities;

import java.io.Serializable;
import java.util.Objects;

/**
 * Clase que contiene las IDs de la entidad Revise
 * @author UnaiUrtiaga
 */
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

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(this.user_id);
        hash = 17 * hash + Objects.hashCode(this.event_id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ReviseId other = (ReviseId) obj;
        if (!Objects.equals(this.user_id, other.user_id)) {
            return false;
        }
        if (!Objects.equals(this.event_id, other.event_id)) {
            return false;
        }
        return true;
    }   
    
}

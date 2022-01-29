/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kedamosclientside.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entidad revisar que se crea con la relacion de EventManager y Event
 * @author UnaiUrtiaga
 */
@Entity
@XmlRootElement
public class Revise implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /**
     * Grupo de IDs cogidas de las entidades EventManager(User) y Event
     */
    @EmbeddedId
    private ReviseId revise_id;
    
    /**
     * Atributo tipo fecha que guarda cuando ha sido revisado el evento
     */
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date revisionDate;
    
    /**
     * Atributo primario que guarda el manager que ha revisado el evento
     */
    @MapsId("user_id")
    @ManyToOne
    private EventManager eventManager;
    
    /**
     * Atributo primario que guarda el evento que ha sido revisado
     */
    @MapsId("event_id")
    @ManyToOne
    private Event event;

    /**
     * Get de los IDs de la revision
     * @return Devuelve la clase ReviseId que contiene todos los IDs de la entidad
     */
    public ReviseId getId() {
        return revise_id;
    }

    /**
     * Set de los IDs de la revision
     * @param id 
     */
    public void setId(Long id) {
        this.revise_id = revise_id;
    }

    /**
     * Get de la fecha de revision
     * @return Devuelve la fecha de la revision
     */
    public Date getRevisionDate() {
        return revisionDate;
    }

    /**
     * Set de la fecha de revision
     * @param revisionDate 
     */
    public void setRevisionDate(Date revisionDate) {
        this.revisionDate = revisionDate;
    }

    public ReviseId getRevise_id() {
        return revise_id;
    }

    public void setRevise_id(ReviseId revise_id) {
        this.revise_id = revise_id;
    }

    public EventManager getEventManager() {
        return eventManager;
    }

    /**
     * Get del manager que ha revisado el evento
     * @return Devuelve el manager
     */
    public void setEventManager(EventManager eventManager) {    
        this.eventManager = eventManager;
    }

    /**
     * Get del evento que ha sido revisado
     * @return Devuelve el evento
     */
    public Event getEvent() {
        return event;
    }

    /**
     * Set del evento que ha sido revisado
     * @param event 
     */
    public void setEvent(Event event) {
        this.event = event;
    }

    /**
     * HashCode de todos los atributos
     * @return Devuelve un HashCode
     */
    @Override    
    public int hashCode() {
        int hash = 0;
        hash += (revise_id != null ? revise_id.hashCode() : 0);
        return hash;
    }

    /**
     * Equals que comprueba el valor de dos revisiones
     * @param obj
     * @return Devuelve un booleano
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Revise)){
            return false;
        }
        Revise other = (Revise) object;
        if ((this.revise_id == null && other.revise_id != null) || (this.revise_id != null && !this.revise_id.equals(other.revise_id))){
            return false;
        }
        return true;
    }

    /**
     * ToString para crear un string con todos los datos de la revision
     * @return Devuelve los datos de la revision
     */
    @Override
    public String toString() {
        return "Revise{" + "revise_id=" + revise_id + ", reviseDate=" + revisionDate + ", reviser=" + eventManager + ", event=" + event + '}';
    }

    
    
}

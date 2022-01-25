/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kedamosclientside.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import kedamosclientside.entities.Category;
import kedamosclientside.entities.Client;
import kedamosclientside.entities.Comment;
import kedamosclientside.entities.PersonalResource;
import kedamosclientside.entities.Revise;
import kedamosclientside.entities.Place;

/**
 * Entidad que contiene todos los datos relacionados con los Eventos
 *
 * @author Adrian Franco
 */
@XmlRootElement
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * Atributo clave primaria con la que se identifican los eventos
     */
    private SimpleLongProperty event_id;

    /**
     * Atributo que rellena el cliente al crear un Evento para que los otros
     * usuarios vean la fecha de inicio
     */
    private Date date;

    /**
     * Atributos para saber cuantos clientes hay apuntados al evento, el mínimo
     * para que se realize y el máximo para apuntarse
     */
    private SimpleLongProperty maxParticipants, minParticipants, actualParticipants;

    /**
     * Breve descripcion sobre el evento que se va a crear
     */
    private SimpleStringProperty description;

    /**
     * Coste de unirse a un evento en concreto
     */
    private SimpleFloatProperty price;

    /**
     * Enumeracion de todas las categorias que puede seleccionar el Cliente al
     * crear Eventos
     */
    private Set<Category> category;

    /**
     * Titulo que asigna el cliente al Evento
     */
    @NotNull
    private SimpleStringProperty title;

    /**
     * Lista de Clientes apuntados al Evento
     */
    private Set<Client> client;

    /**
     * Lista de comentarios de los usuarios sonbre el Evento
     */
    private Set<Comment> comment;

    /**
     * Personal necesario para el evento
     */
    private Set<PersonalResource> personalResource;
    /**
     *
     */
    private Set<Revise> eventRevisions;

    /**
     * Lugar en el que se hace el evento
     */
    private Set<Place> place;

    /**
     * Atributo que define al organizador de cada evento
     */
    private Set<Client> organizer;
    //Constructor

    public Event() {
        this.event_id=new SimpleLongProperty();
        this.date=new Date();
        this.maxParticipants=new SimpleLongProperty();
        this.minParticipants=new SimpleLongProperty();
        this.actualParticipants=new SimpleLongProperty();
        this.description=new SimpleStringProperty();
        this.price=new SimpleFloatProperty();
        this.title= new SimpleStringProperty();
        this.category=new HashSet<>();
        this.comment=new HashSet<>();
        this.personalResource=new HashSet<>();
        this.eventRevisions=new HashSet<>();
        this.place=new HashSet<>();
        this.organizer=new HashSet<>();
    }
    
    //Getters & Setters   

    public SimpleLongProperty getEvent_id() {
        return event_id;
    }

    public void setEvent_id(SimpleLongProperty event_id) {
        this.event_id = event_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getMaxParticipants() {
        return maxParticipants.get();
    }

    public void setMaxParticipants(Long maxParticipants) {
        this.maxParticipants.set(maxParticipants);
    }

    public Long getMinParticipants() {
        return minParticipants.get();
    }

    public void setMinParticipants(Long minParticipants) {
        this.minParticipants.set(minParticipants);
    }

    public Long getActualParticipants() {
        return actualParticipants.get();
    }

    public void setActualParticipants(Long actualParticipants) {
        this.actualParticipants.set(actualParticipants);
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public Float getPrice() {
        return price.get();
    }

    public void setPrice(Float price) {
        this.price.set(price);
    }

    public Set<Category> getCategory() {
        return category;
    }

    public void setCategory(Set<Category> category) {
        this.category = category;
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    @XmlTransient
    public Set<Client> getClient() {
        return client;
    }

    public void setClient(Set<Client> client) {
        this.client = client;
    }

    public Set<Comment> getComment() {
        return comment;
    }

    public void setComment(Set<Comment> comment) {
        this.comment = comment;
    }
    
    @XmlTransient
    public Set<PersonalResource> getPersonalResource() {
        return personalResource;
    }

    public void setPersonalResource(Set<PersonalResource> personalResource) {
        this.personalResource = personalResource;
    }

    @XmlTransient
    public Set<Revise> getEventRevisions() {
        return eventRevisions;
    }

    public void setEventRevisions(Set<Revise> eventRevisions) {
        this.eventRevisions = eventRevisions;
    }
    
    @XmlTransient
    public Set<Place> getPlace() {
        return place;
    }

    public void setPlace(Set<Place> place) {
        this.place = place;
    }
    
    @XmlTransient
    public Set<Client> getOrganizer() {
        return organizer;
    }

    public void setOrganizer(Set<Client> organizer) {
        this.organizer = organizer;
    }

    @Override
    /**
     * Representacion entera para la instancia de Evento
     */
    public int hashCode() {
        int hash = 0;
        hash += (event_id != null ? event_id.hashCode() : 0);
        return hash;
    }

    /**
     * Compara la igualdad de dos objetos de evento. Este metodo considera que
     * una cuenta es igual a otra cuando sus ids son exactamente iguales
     *
     * @param object El otro objeto evento con el que se compara
     * @return True si las comparaciones son iguales
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Event)) {
            return false;
        }
        Event other = (Event) object;
        if ((this.event_id == null && other.event_id != null) || (this.event_id != null && !this.event_id.equals(other.event_id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Event{" + "event_id=" + event_id + ", date=" + date + ", maxParticipants=" + maxParticipants + ", minParticipants=" + minParticipants + ", actualParticipants=" + actualParticipants + ", description=" + description + ", price=" + price + ", category=" + category + ", title=" + title + ", client=" + client + ", comment=" + comment + ", personalResource=" + personalResource + ", eventRevisions=" + eventRevisions + ", place=" + place + ", organizer=" + organizer + '}';
    }

}

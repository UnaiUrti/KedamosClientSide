/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kedamosclientside.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entidad que contiene todos los datos relacionados con los Eventos
 *
 * @author Adrian Franco, Irkus de la Fuente
 */
@XmlRootElement
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * Atributo clave primaria con la que se identifican los eventos
     */
    private Long event_id;

    /**
     * Atributo que rellena el cliente al crear un Evento para que los otros
     * usuarios vean la fecha de inicio
     */
    private Date date;

    /**
     * Atributos para saber cuantos clientes hay apuntados al evento, el mínimo
     * para que se realize y el máximo para apuntarse
     */
    private Long maxParticipants, minParticipants, actualParticipants;

    /**
     * Breve descripcion sobre el evento que se va a crear
     */
    private String description;

    /**
     * Coste de unirse a un evento en concreto
     */
    private Float price;
    
    /**
     * Enumeracion de todas las categorias que puede seleccionar el Cliente al
     * crear Eventos
     */
    private Category category;

    /**
     * Titulo que asigna el cliente al Evento
     */
    private String title;

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


    @XmlTransient
    public Set<Revise> getEventRevisions() {
        return eventRevisions;
    }

    public void setEventRevisions(Set<Revise> eventRevisions) {
        this.eventRevisions = eventRevisions;
    }
    /**
     * Lugar en el que se hace el evento
     */
    private Place place;

    /**
     * Atributo que define al organizador de cada evento
     */
    private Client organizer;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(Long maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public Long getMinParticipants() {
        return minParticipants;
    }

    public void setMinParticipants(Long minParticipants) {
        this.minParticipants = minParticipants;
    }

    public Long getActualParticipants() {
        return actualParticipants;
    }

    public void setActualParticipants(Long actualParticipants) {
        this.actualParticipants = actualParticipants;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @XmlTransient
    public Set<Client> getClient() {
        return client;
    }

    public void setClient(Set<Client> client) {
        this.client = client;
    }

    @XmlTransient
    public Set<Comment> getComment() {
        return comment;
    }

    public void setComment(Set<Comment> comment) {
        this.comment = comment;
    }
    
    //Depende de como lo queramos hacer
    @XmlTransient
    public Set<PersonalResource> getPersonalResource() {
        return personalResource;
    }

    public void setPersonalResource(Set<PersonalResource> personalResource) {
        this.personalResource = personalResource;
    }
    
    @XmlTransient
    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }
    
    @XmlTransient
    public Client getOrganizer() {
        return organizer;
    }

    public void setOrganizer(Client organizer) {
        this.organizer = organizer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getEvent_id() {
        return event_id;
    }

    public void setEvent_id(Long event_id) {
        this.event_id = event_id;
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
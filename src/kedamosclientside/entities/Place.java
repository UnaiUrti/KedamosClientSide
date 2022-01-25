/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kedamosclientside.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class Place implements Serializable {

    private static final long serialVersionUID = 1L;

    public Place() {
        this.place_id = new SimpleLongProperty();
        this.address = new SimpleStringProperty();
        this.name = new SimpleStringProperty();
        this.price = new SimpleFloatProperty();
        this.dateRenewal = new Date();
        this.events = new HashSet<>();
    }
    
    
    
    
    /**
     * Atributo id de la entidad
     */
    private SimpleLongProperty place_id;
    
    /**
     * Atributo de la direccion del lugar
     * Es unico porque no puede mas de un sitio con la misma direccion
     */
    @NotNull
    private SimpleStringProperty address;
    
    /**
     * Atributo del nombre del lugar
     * Es un campo que no puede dejarse nulo
     */
    @NotNull
    private SimpleStringProperty name;
    
    /**
     * Atributo del precio que vale el lugar (polideportivos y tal)
     * Es un campo que se puede dejar nulo porque puede que el sitio sea gratuito
     */
    private SimpleFloatProperty price;
    
    /**
     * Atributo de la fecha de renovacion del sitio
     * Es un campo que puede dejarse nulo porque puede que el sitio no se haya
     * renovado
     */
    private Date dateRenewal;
    
    /**
     * Atributo de una lista de eventos
     * Aqui se van a guardar todos los eventos que se realizan en este lugar
     */
    private Set<Event> events;

    /**
     * Get del id
     * @return devuelve la id
     */
    public Long getId() {
        return this.place_id.get();
    }

    /**
     * Set del id
     * @param place_id 
     */
    public void setId(Long place_id) {
        this.place_id.set(place_id);
    }

    /**
     * Get del address
     * @return devuelve la direccion
     */
    public String getAddress() {
        return this.address.get();
    }

    /**
     * Set del address
     * @param address 
     */
    public void setAddress(String address) {
        this.address.set(address);
    }

    /**
     * Get del nombre
     * @return devuelve el nombre
     */
    public String getName() {
        return this.name.get();
    }

    /**
     * Set del nombre
     * @param name 
     */
    public void setName(String name) {
        this.name.set(name);
    }

    /**
     * Get del precio
     * @return devuelve el precio
     */
    public Float getPrice() {
        return this.price.get();
    }
    
    /**
     * Set del precio
     * @param price 
     */
    public void setPrice(Float price) {
        this.price.set(price);
    }

    /**
     * Get de la fecha de renovacion
     * @return devuelve la fecha de renovacion
     */
    public Date getDateRenewal() {
        return this.dateRenewal;
    }

    /**
     * Set de la fecha de renovacion
     * @param dateRenewal 
     */
    public void setDateRenewal(Date dateRenewal) {
        this.dateRenewal = dateRenewal;
    }

    /**
     * Get de los eventos organizados en el lugar
     * @return devuelve una lista de eventos
     */
    //@XmlTransient
    public Set<Event> getEvents() {
        return events;
    }

    /**
     * Set de los eventos organizados en el lugar
     * @param event 
     */
    public void setEvents(Set<Event> events) {
        this.events = events;
    }
    
    

    /**
     * Hash code de la entidad
     * @return Devuelve el hash
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 73 * hash + Objects.hashCode(this.place_id);
        hash = 73 * hash + Objects.hashCode(this.address);
        hash = 73 * hash + Objects.hashCode(this.name);
        hash = 73 * hash + Objects.hashCode(this.price);
        hash = 73 * hash + Objects.hashCode(this.dateRenewal);
        hash = 73 * hash + Objects.hashCode(this.events);
        return hash;
    }

    /**
     * Metodo equals de la entidad
     * @param object
     * @return devuelve un booleano
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Place)) {
            return false;
        }
        Place other = (Place) object;
        if ((this.place_id == null && other.place_id != null) || (this.place_id != null && !this.place_id.equals(other.place_id))) {
            return false;
        }
        return true;
    }

    /**
     * Metoto toString de la entidad
     * @return devuelve un string con toda la info
     */
    @Override
    public String toString() {
        return "Place{" + "place_id=" + place_id + ", address=" + address + ", name=" + name + ", price=" + price + ", dateRenewal=" + dateRenewal + ", event=" + events + '}';
    }
    
}

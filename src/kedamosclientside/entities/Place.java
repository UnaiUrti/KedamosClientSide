/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kedamosclientside.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Set;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entidad Place la que va a contener los sitios en los que se van a hacer los eventos
 * @author UnaiUrtiaga
 */
@Entity
@Table (name="place", schema="kedamosdb")
@XmlRootElement
public class Place implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /**
     * Atributo id de la entidad
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    private Long place_id;
    
    /**
     * Atributo de la direccion del lugar
     * Es unico porque no puede mas de un sitio con la misma direccion
     */
    @Column(unique=true)
    @NotNull
    private String address;
    
    /**
     * Atributo del nombre del lugar
     * Es un campo que no puede dejarse nulo
     */
    @NotNull
    private String name;
    
    /**
     * Atributo del precio que vale el lugar (polideportivos y tal)
     * Es un campo que se puede dejar nulo porque puede que el sitio sea gratuito
     */
    private Float price;
    
    /**
     * Atributo de la fecha de renovacion del sitio
     * Es un campo que puede dejarse nulo porque puede que el sitio no se haya
     * renovado
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateRenewal;
    
    /**
     * Atributo de una lista de eventos
     * Aqui se van a guardar todos los eventos que se realizan en este lugar
     */
    @OneToMany(cascade=ALL, mappedBy="place")
    private Set<Event> event;

    /**
     * Get del id
     * @return devuelve la id
     */
    public Long getId() {
        return place_id;
    }

    /**
     * Set del id
     * @param place_id 
     */
    public void setId(Long place_id) {
        this.place_id = place_id;
    }

    /**
     * Get del address
     * @return devuelve la direccion
     */
    public String getAddress() {
        return address;
    }

    /**
     * Set del address
     * @param address 
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Get del nombre
     * @return devuelve el nombre
     */
    public String getName() {
        return name;
    }

    /**
     * Set del nombre
     * @param name 
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get del precio
     * @return devuelve el precio
     */
    public Float getPrice() {
        return price;
    }
    
    /**
     * Set del precio
     * @param place 
     */
    public void setPrice(Float price) {
        this.price = price;
    }

    /**
     * Get de la fecha de renovacion
     * @return devuelve la fecha de renovacion
     */
    public Date getDateRenewal() {
        return dateRenewal;
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
    @XmlTransient
    public Set<Event> getEvent() {
        return event;
    }

    /**
     * Set de los eventos organizados en el lugar
     * @param event 
     */
    public void setEvent(Set<Event> event) {
        this.event = event;
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
        hash = 73 * hash + Objects.hashCode(this.event);
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
        return "Place{" + "place_id=" + place_id + ", address=" + address + ", name=" + name + ", price=" + price + ", dateRenewal=" + dateRenewal + ", event=" + event + '}';
    }
    
}

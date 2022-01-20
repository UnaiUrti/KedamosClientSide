
package kedamosclientside.entities;

import java.io.Serializable;
import java.util.Objects;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import javax.xml.bind.annotation.XmlRootElement;


/**
 *Entidad PersonalResource 
 * @author Irkus de la Fuente
 */

@XmlRootElement
public class PersonalResource implements Serializable {
    //Atributos
    private static final long serialVersionUID = 1L;
    /**
     * Clave primaria de la entidad
     */
 
    private SimpleLongProperty personalresource_id;
    /**
     * Cantidad del personal requerida
     */
    private SimpleLongProperty quantity;
    /**
     * Enumeracion que indica que tipo de personal es
     */

    private SimpleObjectProperty<Type> type;
    /**
     * Precio del personal
     */
    private SimpleFloatProperty price;
    /**
     * Fecha de fin de contrato
     */

    private SimpleStringProperty dateExpired;
    /**
     * Fecha contratado
     */

    private SimpleStringProperty dateHired;
    
    //Campo relacional
    /**
     * Este es el campo relacional el cual une los eventos mediante la id
     */

    private SimpleObjectProperty<Event> event;
    
    //Getters y setters
    /**
     * Get de la id
     * @return personalresource_id
     */
    public Long getPersonalresource_id() {
        return this.personalresource_id.get();
    }
/**
 * Get de la cantidad
 * @return quantity
 */
    public Long getQuantity() {
        return this.quantity.get();
    }
/**
 * Set de la cantidad
 * @param quantity 
 */
    public void setQuantity(Long quantity) {
        this.quantity.set(quantity);
    }
/**
 * Set del tipo
 * @return type
 */
    public Type getType() {
        return this.type.get();
    }
/**
 * Set del tipo
 * @param type 
 */
    public void setType(Type type) {
        this.type.set(type);
    }
/**
 * Get del precio
 * @return price
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
 * Get de la fecha de fin de contrato
 * @return dateExpired
 */
    public String getDateExpired() {
        return this.dateExpired.get();
    }
/**
 * Set de la fecha de fin de contrato
 * @param dateExpired 
 */
    public void setDateExpired(String dateExpired) {
        this.dateExpired.set(dateExpired);
    }
/**
 * Get de fecha de contratacion
 * @return dateHired
 */
    public String getDateHired() {
        return this.dateHired.get();
    }
/**
 * Set de fecha de contratacion
 * @param dateHired 
 */
    public void setDateHired(String dateHired) {
        this.dateHired.set(dateHired);
    }
/**
 * Get del campo relacional
 * @return event
 */
     //@XmlTransient
    public Event getEvent() {
        return this.event.get();
    }
/**
 * Set del campo relacional
 * @param event 
 */
    public void setEvent(Event event) {
        this.event.set(event);
    }
/**
 * Set del id
     * @param personalresource_id 
 */
    public void setPersonalresource_id(Long personalresource_id) {
        this.personalresource_id.set(personalresource_id);
    }
    //HashCode
/**
 * Hashcode
 * @return hash
 */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.personalresource_id);
        hash = 37 * hash + Objects.hashCode(this.quantity);
        hash = 37 * hash + Objects.hashCode(this.type);
        hash = 37 * hash + Objects.hashCode(this.price);
        hash = 37 * hash + Objects.hashCode(this.dateExpired);
        hash = 37 * hash + Objects.hashCode(this.dateHired);
        hash = 37 * hash + Objects.hashCode(this.event);
        return hash;
    }

  //Equals
/**
 * Equals
 * @param object
 * @return boolean
 */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PersonalResource)) {
            return false;
        }
        PersonalResource other = (PersonalResource) object;
        if ((this.personalresource_id == null && other.personalresource_id != null) || (this.personalresource_id != null && !this.personalresource_id.equals(other.personalresource_id))) {
            return false;
        }
        return true;
    }
    //ToStirng
/**
 * ToString
 * @return String
 */
    @Override
    public String toString() {
        return "PersonalResource{" + "id=" + personalresource_id + ", quantity=" + quantity + ", type=" + type + ", price=" + price + ", dateExpired=" + dateExpired + ", dateHired=" + dateHired + ", event=" + event + '}';
    }

    
    
}

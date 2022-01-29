package kedamosclientside.entities;

import java.io.Serializable;
import java.util.Set;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entidad que representa al usuario que es un cliente. 
 * @author Steven Arce
 */
@Entity
@DiscriminatorValue("client")
@XmlRootElement
public class Client extends User implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long accountNumber;
    private boolean isPremium;

    @OneToMany(mappedBy = "organizer", cascade = ALL)
    private Set<Event> myEvents;
    
    @ManyToMany(cascade = ALL)
    @JoinTable(name = "user_event", schema = "kedamosdb",
               joinColumns = @JoinColumn(name = "user_id"),
               inverseJoinColumns = @JoinColumn(name = "event_id"))
    private Set<Event> joinEvents; 
    
    @OneToMany(mappedBy = "client", cascade = ALL)
    private Set<Comment> myComments;

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public boolean isIsPremium() {
        return isPremium;
    }

    public void setIsPremium(boolean isPremium) {
        this.isPremium = isPremium;
    }

    @XmlTransient
    public Set<Event> getMyEvents() {
        return myEvents;
    }

    public void setMyEvents(Set<Event> myEvents) {
        this.myEvents = myEvents;
    }

    @XmlTransient
    public Set<Event> getJoinEvents() {
        return joinEvents;
    }

    public void setJoinEvents(Set<Event> joinEvents) {
        this.joinEvents = joinEvents;
    }

    @XmlTransient
    public Set<Comment> getMyComments() {
        return myComments;
    }

    public void setMyComments(Set<Comment> myComments) {
        this.myComments = myComments;
    }
    
}

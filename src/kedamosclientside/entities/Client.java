package kedamosclientside.entities;

import java.io.Serializable;
import java.util.Set;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entidad que representa al usuario que es un cliente.
 * @author Steven Arce
 */
@XmlRootElement
public class Client extends User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long accountNumber;
    private boolean isPremium;

    private Set<Event> myEvents;

    private Set<Event> joinEvents;

    private Set<Comment> myComments;

    public Client() {}
    
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
    
    //@XmlTransient
    public Set<Event> getMyEvents() {
        return myEvents;
    }

    public void setMyEvents(Set<Event> myEvents) {
        this.myEvents = myEvents;
    }
    
    //@XmlTransient
    public Set<Event> getJoinEvents() {
        return joinEvents;
    }

    public void setJoinEvents(Set<Event> joinEvents) {
        this.joinEvents = joinEvents;
    }
    
    //@XmlTransient
    public Set<Comment> getMyComments() {
        return myComments;
    }

    public void setMyComments(Set<Comment> myComments) {
        this.myComments = myComments;
    }

}

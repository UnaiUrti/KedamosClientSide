package kedamosclientside.entities;

import java.io.Serializable;
import java.util.Set;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entidad que representa al usuario que administra los eventos.
 * @author Steven Arce
 */
@Entity
@DiscriminatorValue("event_manager")
@XmlRootElement
public class EventManager extends User implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Category managerCategoy;
    
    @OneToMany(mappedBy = "eventManager", cascade = ALL)
    private Set<Revise> myRevisions;

    public Category getManagerCategoy() {
        return managerCategoy;
    }

    public void setManagerCategoy(Category managerCategoy) {
        this.managerCategoy = managerCategoy;
    }

    @XmlTransient
    public Set<Revise> getMyRevisions() {
        return myRevisions;
    }

    public void setMyRevisions(Set<Revise> myRevisions) {
        this.myRevisions = myRevisions;
    }

    
  
    
   
    
    
}

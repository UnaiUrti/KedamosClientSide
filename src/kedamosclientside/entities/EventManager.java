package kedamosclientside.entities;

import java.io.Serializable;
import java.util.Set;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entidad que representa al usuario que administra los eventos.
 * @author Steven Arce
 */
@XmlRootElement
public class EventManager extends User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Category managerCategory;

    private Set<Revise> myRevisions;

    public Category getManagerCategory() {
        return managerCategory;
    }

    public void setManagerCategory(Category managerCategory) {
        this.managerCategory = managerCategory;
    }

    //@XmlTransient
    public Set<Revise> getMyRevisions() {
        return myRevisions;
    }

    public void setMyRevisions(Set<Revise> myRevisions) {
        this.myRevisions = myRevisions;
    }

}

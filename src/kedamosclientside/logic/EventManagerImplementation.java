package kedamosclientside.logic;

import java.net.ConnectException;
import java.util.Collection;
import java.util.Set;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.GenericType;
import kedamosclientside.entities.EventManager;
import kedamosclientside.exceptions.ServerDown;
import kedamosclientside.exceptions.UsernameDoesNotExist;
import kedamosclientside.restful.EventManagerREST;

/**
 * Esta clase implementa la interfaz de logica de {@link EventManagerInterface}.
 *
 * @author Steven Arce
 */
public class EventManagerImplementation implements EventManagerInterface {

    private EventManagerREST webClient;

    /**
     * Crea un objeto EventManagerImplementation. Se construye un cliente web
     * para poder acceder al servicio RESTful.
     */
    public EventManagerImplementation() {
        webClient = new EventManagerREST();
    }

    /**
     * Este metodo crea un nuevo manejador de evento.
     *
     * @param eventManager El objeto EventManager que se agregara.
     * @throws ServerDown Si el servidor esta apagado.
     */
    @Override
    public void createEventManager(EventManager eventManager) throws ServerDown {
        try {
            webClient.create(eventManager);
        } catch (Exception e) {
            throw new ServerDown("Server down");
        }
    }

    /**
     * Este metodo modificara los datos de un manejador de evento existente.
     *
     * @param eventManager El objeto EventManager que se editara.
     * @throws ServerDown Si el servidor esta apagado.
     */
    @Override
    public void editEventManager(EventManager eventManager) throws ServerDown {
        try {
            webClient.edit(eventManager, eventManager.getUser_id());
        } catch (Exception e) {
            throw new ServerDown("Server down");
        }
    }

    /**
     * Este metodo eliminara un manejador de evento existente.
     *
     * @param eventManager El objeto EventManager que se eliminara.
     * @throws ServerDown Si el servidor esta apagado.
     */
    @Override
    public void removeEventManager(EventManager eventManager) throws ServerDown {
        try {
            webClient.remove(eventManager.getUser_id());
        } catch (Exception e) {
            throw new ServerDown("Server down");
        }
    }

    /**
     * Este metodo busca un manejador de evento existente.
     *
     * @param eventManager El objeto EventManager que buscara el servidor.
     * @return Devuelve el manejador de evento encontrado.
     * @throws ServerDown Si el servidor esta apagado.
     */
    @Override
    public EventManager findEventManager(EventManager eventManager) throws ServerDown {
        EventManager eventManagerBean;
        try {
            eventManagerBean = webClient.find(new GenericType<EventManager>() {
            }, eventManager.getUser_id());
        } catch (Exception e) {
            throw new ServerDown("Server down");
        }
        return eventManagerBean;

    }

    /**
     * Este metodo retorna una coleccion de manejadores de eventos con todos sus
     * datos.
     *
     * @return @throws ServerDown Si el servidor esta apagado.
     */
    @Override
    public Collection<EventManager> findAll() throws ServerDown {
        Set<EventManager> eventManagers;
        try {
            eventManagers = webClient.findAll(new GenericType<Set<EventManager>>() {
            });
        } catch (Exception e) {
            throw new ServerDown("Server down");
        }
        return eventManagers;
    }

    /**
     * Este metodo busca un manejador de evento mediante su nombre de usuario.
     *
     * @param eventManager
     * @return Devuelve el manejador de evento encontrado.
     * @throws UsernameDoesNotExist Si el nombre de usuario no existe.
     * @throws ServerDown Si el servidor esta apagado.
     */
    @Override
    public EventManager getEventManagerByUsername(EventManager eventManager) throws UsernameDoesNotExist, ServerDown {
        EventManager eventManagerBean;
        try {
            eventManagerBean = webClient.getEventManagerByUsername(new GenericType<EventManager>() {
            }, eventManager.getUsername());

        } catch (NotFoundException e) {
            throw new UsernameDoesNotExist("User not Exist");
        } catch (Exception e) {
            throw new ServerDown("Server down");
        }
        return eventManagerBean;
    }
}

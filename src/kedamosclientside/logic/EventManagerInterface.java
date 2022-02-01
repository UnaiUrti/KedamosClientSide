package kedamosclientside.logic;

import java.util.Collection;
import kedamosclientside.entities.EventManager;
import kedamosclientside.exceptions.ServerDown;
import kedamosclientside.exceptions.UsernameDoesNotExist;

/**
 * Interfaz de logica que encapsula los metodos para le gestion de manejadores
 * de eventos.
 *
 * @author Steven Arce
 */
public interface EventManagerInterface {

    /**
     * Este metodo crea un nuevo manejador de evento.
     *
     * @param eventManager El objeto EventManager que se agregara.
     * @throws ServerDown Si el servidor esta apagado.
     */
    public void createEventManager(EventManager eventManager) throws ServerDown;

    /**
     * Este metodo modificara los datos de un manejador de evento existente.
     *
     * @param eventManager El objeto EventManager que se editara.
     * @throws ServerDown Si el servidor esta apagado.
     */
    public void editEventManager(EventManager eventManager) throws ServerDown;

    /**
     * Este metodo eliminara un manejador de evento existente.
     *
     * @param eventManager El objeto EventManager que se eliminara.
     * @throws ServerDown Si el servidor esta apagado.
     */
    public void removeEventManager(EventManager eventManager) throws ServerDown;

    /**
     * Este metodo busca un manejador de evento existente.
     *
     * @param eventManager El objeto EventManager que buscara el servidor.
     * @return Devuelve el manejador de evento encontrado.
     * @throws ServerDown Si el servidor esta apagado.
     */
    public EventManager findEventManager(EventManager eventManager) throws ServerDown;

    /**
     * Este metodo retorna una coleccion de manejadores de eventos con todos sus
     * datos.
     *
     * @return @throws ServerDown Si el servidor esta apagado.
     */
    public Collection<EventManager> findAll() throws ServerDown;

    /**
     * Este metodo busca un manejador de evento mediante su nombre de usuario.
     *
     * @param eventManager
     * @return Devuelve el manejador de evento encontrado.
     * @throws UsernameDoesNotExist Si el nombre de usuario no existe.
     * @throws ServerDown Si el servidor esta apagado.
     */
    public EventManager getEventManagerByUsername(EventManager eventManager) throws UsernameDoesNotExist, ServerDown;

}

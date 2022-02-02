package kedamosclientside.logic;

import java.util.Collection;
import kedamosclientside.entities.Client;
import kedamosclientside.exceptions.EmailDoesNotExist;
import kedamosclientside.exceptions.PasswordIncorrect;
import kedamosclientside.exceptions.ServerDown;
import kedamosclientside.exceptions.UsernameDoesNotExist;

/**
 * Interfaz de logica que encapsula los metodos para la gestion de clientes.
 *
 * @author Steven Arce
 */
public interface ClientInterface {

    /**
     * Este metodo crea un nuevo cliente.
     *
     * @param client El objeto Client que se agregara.
     * @throws ServerDown Si el servidor esta apagado.
     */
    public void createClient(Client client) throws ServerDown;

    /**
     * Este metodo modificara los datos de un cliente existente.
     *
     * @param client El objeto Client que se editara.
     * @throws ServerDown Si el servidor esta apagado.
     */
    public void editClient(Client client) throws ServerDown;

    /**
     * Este metodo eliminara un cliente existente.
     *
     * @param client El objeto Client que se eliminara.
     * @throws ServerDown Si el servidor esta apagado.
     */
    public void removeClient(Client client) throws ServerDown;

    /**
     * Este metodo busca un cliente existente.
     *
     * @param client El objeto Client que buscara el servidor.
     * @return Devuelve el cliente encontrado.
     * @throws ServerDown Si el servidor esta apagado.
     */
    public Client findClient(Client client) throws ServerDown;

    /**
     * Este metodo retorna una coleccion de clientes con todos sus datos.
     *
     * @return Coleccion con todos los datos de los clientes.
     * @throws ServerDown Si el servidor esta apagado.
     */
    public Collection<Client> findAllClient() throws ServerDown;

    /**
     * Este metodo resetea la contraseña del cliente y se lo envia mediante un
     * correo electronico.
     *
     * @param client El objeto Client a buscar para hacer el reseteo de
     * contraseña.
     * @return Devuelve el cliente encontrado con la contraseña cambiada.
     * @throws EmailDoesNotExist Si el email no existe.
     * @throws ServerDown Si el servidor esta apagado.
     */
    public Client resetPassword(Client client) throws EmailDoesNotExist, ServerDown;

    /**
     * Este metodo cambia la contraseña del cliente por el nuevo introducido.
     *
     * @param client El objeto Client al que se le va a cambiar la contraseña.
     * @throws ServerDown Si el servidor esta apagado.
     */
    public void changePassword(Client client) throws ServerDown;

    /**
     * Este metodo valida si la contraseña introducida por el cliente es la
     * misma que la que esta en la base de datos.
     *
     * @param client El objeto Client al que se le va a validar la contraseña
     * introducida.
     * @return Devuelve el cliente con la nueva contraseña introducida.
     * @throws PasswordIncorrect Si la contraseña introducida no coincide.
     * @throws ServerDown Si el servidor esta apagado.
     */
    public Client validatePassword(Client client) throws PasswordIncorrect, ServerDown;

    /**
     * Este metodo busca un cliente mediante su nombre de usuario.
     *
     * @param client Objeto Client al que se le va a buscar.
     * @return Devuelve el cliente encontrado.
     * @throws UsernameDoesNotExist Si el nombre de usuario no existe.
     * @throws ServerDown Si el servidor esta apagado.
     */
    public Client getClientByUsername(Client client) throws UsernameDoesNotExist, ServerDown;

}

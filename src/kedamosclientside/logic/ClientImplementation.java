package kedamosclientside.logic;

import java.util.Collection;
import java.util.Set;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.GenericType;
import kedamosclientside.entities.Client;
import kedamosclientside.exceptions.EmailDoesNotExist;
import kedamosclientside.exceptions.PasswordIncorrect;
import kedamosclientside.exceptions.ServerDown;
import kedamosclientside.exceptions.UsernameDoesNotExist;
import kedamosclientside.restful.ClientREST;

/**
 * Esta clase implementa la interfaz de logica de {@link ClientInterface}.
 *
 * @author Steven Arce
 */
public class ClientImplementation implements ClientInterface {

    private ClientREST webClient;

    /**
     * Crea un objeto ClientImplementation. Se construye un cliente web para
     * poder acceder al servicio RESTful.
     */
    public ClientImplementation() {
        webClient = new ClientREST();
    }

    /**
     * Este metodo crea un nuevo cliente.
     *
     * @param client El objeto Client que se agregara.
     * @throws ServerDown Si el servidor esta apagado.
     */
    @Override
    public void createClient(Client client) throws ServerDown {
        try {
            webClient.create(client);
        } catch (Exception ex) {
            throw new ServerDown("Server down, try again later");
        }
    }

    /**
     * Este metodo modificara los datos de un cliente existente.
     *
     * @param client El objeto Client que se editara.
     * @throws ServerDown Si el servidor esta apagado.
     */
    @Override
    public void editClient(Client client) throws ServerDown {
        try {
            webClient.edit(client, client.getUser_id());
        } catch (Exception ex) {
            throw new ServerDown("Server down, try again later");
        }
    }

    /**
     * Este metodo eliminara un cliente existente.
     *
     * @param client El objeto Client que se eliminara.
     * @throws ServerDown Si el servidor esta apagado.
     */
    @Override
    public void removeClient(Client client) throws ServerDown {
        try {
            webClient.remove(client.getUser_id());
        } catch (Exception ex) {
            throw new ServerDown("Server down, try again later");
        }
    }

    /**
     * Este metodo busca un cliente existente.
     *
     * @param client El objeto Client que buscara el servidor.
     * @return Devuelve el cliente encontrado.
     * @throws ServerDown Si el servidor esta apagado.
     */
    @Override
    public Client findClient(Client client) throws ServerDown {
        Client clientBean = null;
        try {
            webClient.find(new GenericType<Client>() {
            }, client.getUser_id());
        } catch (Exception ex) {
            throw new ServerDown("Server down, try again later");
        }
        return clientBean;
    }

    /**
     * Este metodo retorna una coleccion de clientes con todos sus datos.
     *
     * @return Coleccion con todos los datos de los clientes.
     * @throws ServerDown Si el servidor esta apagado.
     */
    @Override
    public Collection<Client> findAllClient() throws ServerDown {
        Set<Client> clients;
        try {
            clients = webClient.findAll(new GenericType<Set<Client>>() {
            });
        } catch (Exception ex) {
            throw new ServerDown("Server down, try again later");
        }
        return clients;
    }

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
    @Override
    public Client resetPassword(Client client) throws EmailDoesNotExist, ServerDown {
        Client clientBean = null;
        try {
            clientBean = webClient.resetPassword(new GenericType<Client>() {
            }, client.getEmail());
        } catch (NotFoundException ex) {
            throw new EmailDoesNotExist("The email does not exist");
        } catch (Exception ex) {
            throw new ServerDown("Server down, try again later");
        }
        return clientBean;
    }

    /**
     * Este metodo cambia la contraseña del cliente por el nuevo introducido.
     *
     * @param client El objeto Client al que se le va a cambiar la contraseña.
     * @throws ServerDown Si el servidor esta apagado.
     */
    @Override
    public void changePassword(Client client) throws ServerDown {
        try {
            webClient.changePassword(client, client.getUser_id());
        } catch (Exception ex) {
            throw new ServerDown("Server down, try again later");
        }
    }

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
    @Override
    public Client validatePassword(Client client) throws PasswordIncorrect, ServerDown {
        Client clientBean = null;
        try {
            clientBean = webClient.validatePassword(new GenericType<Client>() {
            }, client.getUsername(), client.getPassword());

        } catch (NotAuthorizedException ex) {
            throw new PasswordIncorrect("Incorrect Password");
        } catch (Exception ex) {
            throw new ServerDown("Server down, try again later");
        }
        return clientBean;
    }

    /**
     * Este metodo busca un cliente mediante su nombre de usuario
     *
     * @param client Objeto Client al que se le va a buscar.
     * @return Devuelve el cliente encontrado.
     * @throws UsernameDoesNotExist Si el nombre de usuario no existe.
     * @throws ServerDown Si el servidor esta apagado.
     */
    @Override
    public Client getClientByUsername(Client client) throws UsernameDoesNotExist, ServerDown {
        Client clientBean;
        try {
            clientBean = webClient.getClientByUsername(new GenericType<Client>() {
            }, client.getUsername());
        } catch (NotFoundException ex) {
            throw new UsernameDoesNotExist("Username does not exist");
        } catch (Exception ex) {
            throw new ServerDown("Server down, try again later");
        }
        return clientBean;
    }

}

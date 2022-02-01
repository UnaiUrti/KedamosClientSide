package kedamosclientside.logic;

import java.util.Collection;
import java.util.Set;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.GenericType;
import kedamosclientside.entities.User;
import kedamosclientside.exceptions.PasswordIncorrect;
import kedamosclientside.exceptions.ServerDown;
import kedamosclientside.exceptions.UsernameDoesNotExist;
import kedamosclientside.restful.UserREST;

/**
 * Esta clase implementa la interfaz de logica de {@link UserInterface}.
 *
 * @author Steven Arce
 */
public class UserImplementation implements UserInterface {

    private UserREST webClient;

    /**
     * Crea un objeto UserImplementation. Se construye un cliente web para
     * poder acceder al servicio RESTful.
     */
    public UserImplementation() {
        webClient = new UserREST();
    }

    /**
     * Este metodo crea un nuevo usuario.
     *
     * @param user El objeto User que se agregara.
     * @throws ServerDown Si el servidor esta apagado.
     */
    @Override
    public void createUser(User user) throws ServerDown {
        try {
            webClient.create(user);
        } catch (Exception ex) {
            throw new ServerDown("Server down, try again later");
        }
    }

    /**
     * Este metodo modificara los datos de un usuario existente.
     *
     * @param user El objeto User que se editara.
     * @throws ServerDown Si el servidor esta apagado.
     */
    @Override
    public void editUser(User user) throws ServerDown {
        try {
            webClient.edit(user, user.getUser_id());
        } catch (Exception ex) {
            throw new ServerDown("Server down, try again later");
        }
    }

    /**
     * Este metodo eliminara un usuario existente.
     *
     * @param user El objeto User que se eliminara.
     * @throws ServerDown Si el servidor esta apagado.
     */
    @Override
    public void removeUser(User user) throws ServerDown {
        try {
            webClient.remove(user.getUser_id());
        } catch (Exception ex) {
            throw new ServerDown("Server down, try again later");
        }

    }

    /**
     * Este metodo busca un usuaio existente.
     *
     * @param user El objeto User que buscara el servidor.
     * @return Devuelve el usuario encontrado.
     * @throws ServerDown Si el servidor esta apagado.
     */
    @Override
    public User findUser(User user) throws ServerDown {
        User userBean;
        try {
            userBean = webClient.find(new GenericType<User>() {
            }, user.getUser_id());
        } catch (Exception ex) {
            throw new ServerDown("Server down, try again later");
        }
        return userBean;
    }

    /**
     * Este metodo retorna una coleccion de usuarion con todos sus datos.
     *
     * @return Coleccion con todos los datos de los usuarios.
     * @throws ServerDown Si el servidor esta apagado.
     */
    @Override
    public Collection<User> findAllUser() throws ServerDown {
        Set<User> users;
        try {
            users = webClient.findAll(new GenericType<Set<User>>() {
            });
        } catch (Exception ex) {
            throw new ServerDown("Server down, try again later");
        }
        return users;
    }

    /**
     * Este metodo valida el usuario y la contraseña que se introduce en el
     * login.
     *
     * @param user El objeto User que buscara el servidor.
     * @return Coleccion con los datos del usuario del login.
     * @throws UsernameDoesNotExist Si el username no existe.
     * @throws PasswordIncorrect Si la contraseña del usuario es incorrecto.
     * @throws ServerDown Si el servidor esta apagado.
     */
    @Override
    public Collection<User> LoginValidation(User user) throws UsernameDoesNotExist,
            PasswordIncorrect, ServerDown {
        Set<User> users = null;
        try {
            users = webClient.validateLogin(new GenericType<Set<User>>() {
            }, user.getUsername(), user.getPassword());
        } catch (NotFoundException ex) {
            throw new UsernameDoesNotExist("Username dont exists");
        } catch (NotAuthorizedException ex) {
            throw new PasswordIncorrect("Incorrect Password");
        } catch (Exception ex) {
            throw new ServerDown("Server down, try again later");
        }
        return users;
    }
}

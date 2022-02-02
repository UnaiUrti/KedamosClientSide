package kedamosclientside.logic;

import java.util.Collection;
import kedamosclientside.entities.User;
import kedamosclientside.exceptions.PasswordIncorrect;
import kedamosclientside.exceptions.ServerDown;
import kedamosclientside.exceptions.UsernameDoesNotExist;

/**
 * Interfaz de logica que encapsula los metodos para la gestion de usuarios.
 *
 * @author Steven Arce
 */
public interface UserInterface {

    /**
     * Este metodo crea un nuevo usuario.
     *
     * @param user El objeto User que se agregara.
     * @throws ServerDown Si el servidor esta apagado.
     */
    public void createUser(User user) throws ServerDown;

    /**
     * Este metodo modificara los datos de un usuario existente.
     *
     * @param user El objeto User que se editara.
     * @throws ServerDown Si el servidor esta apagado.
     */
    public void editUser(User user) throws ServerDown;

    /**
     * Este metodo eliminara un usuario existente.
     *
     * @param user El objeto User que se eliminara.
     * @throws ServerDown Si el servidor esta apagado.
     */
    public void removeUser(User user) throws ServerDown;

    /**
     * Este metodo busca un usuaio existente.
     *
     * @param user El objeto User que buscara el servidor.
     * @return Devuelve el usuario encontrado.
     * @throws ServerDown Si el servidor esta apagado.
     */
    public User findUser(User user) throws ServerDown;

    /**
     * Este metodo retorna una coleccion de usuarios con todos sus datos.
     *
     * @return Coleccion con todos los datos de los usuarios.
     * @throws ServerDown Si el servidor esta apagado.
     */
    public Collection<User> findAllUser() throws ServerDown;

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
    public Collection<User> LoginValidation(User user) throws UsernameDoesNotExist,
            PasswordIncorrect, ServerDown;
}

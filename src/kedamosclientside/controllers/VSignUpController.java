package kedamosclientside.controllers;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import kedamosclientside.entities.Client;
import kedamosclientside.entities.User;
import kedamosclientside.entities.UserStatus;
import kedamosclientside.exceptions.ServerDown;
import kedamosclientside.logic.ClientFactory;
import kedamosclientside.logic.ClientInterface;
import kedamosclientside.logic.UserFactory;
import kedamosclientside.logic.UserInterface;
import kedamosclientside.security.Crypt;

/**
 * Clase controladora de la ventana singUp.
 *
 * @author Steven Arce
 */
public class VSignUpController {

    private final static Logger logger = Logger.getLogger("kedamosclientside.controllers.VSignUpController");
    private Stage stage;
    private Tooltip tooltip;

    @FXML
    private TextField txtUsername, txtEmail, txtFullName;
    @FXML
    private PasswordField txtPassword, txtConfirmPassword;
    @FXML
    private Button btnSignUp, btnBack;
    @FXML
    private Label lblUsername;
    @FXML
    private Label lblEmail;
    @FXML
    private Label lblFullName;
    @FXML
    private Label lblPassword;
    @FXML
    private Label lblConfirmPassword;

    private UserInterface ui = UserFactory.getUserImplementation();
    private ClientInterface ci = ClientFactory.getClientImplementation();

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Este metodo pretende inicializar el la escena y el escenario.
     *
     * @param root nodo del grafo de la escena
     */
    public void initStage(Parent root) {
        logger.info("Iniciado initStage de la ventana signUp");

        Scene scene = new Scene(root);
        stage.setTitle("SignUp");

        //Todos los campos y botones habilitados
        txtUsername.setDisable(false);
        txtEmail.setDisable(false);
        txtFullName.setDisable(false);
        txtPassword.setDisable(false);
        txtConfirmPassword.setDisable(false);
        btnSignUp.setDisable(false);
        btnBack.setDisable(false);
        //Se enfoca en el campo username
        txtUsername.requestFocus();
        //Esta ventana no se puede redimensionar
        stage.setResizable(false);

        //Tooltip en todos los campos para indicar lo que hay que introducir
        tooltip = new Tooltip("Enter a username");
        txtUsername.setTooltip(tooltip);
        tooltip = new Tooltip("Enter an email");
        txtEmail.setTooltip(tooltip);
        tooltip = new Tooltip("Enter your full name");
        txtFullName.setTooltip(tooltip);
        tooltip = new Tooltip("Enter a password");
        txtPassword.setTooltip(tooltip);
        tooltip = new Tooltip("Repeat the entered password");
        txtConfirmPassword.setTooltip(tooltip);

        //Limitar la entrada de maximo 50 caracteres (ChangeListener)
        txtUsername.textProperty().addListener(this::limitCharacters);
        txtEmail.textProperty().addListener(this::limitCharacters);
        txtFullName.textProperty().addListener(this::limitCharacters);
        txtPassword.textProperty().addListener(this::limitCharacters);
        txtConfirmPassword.textProperty().addListener(this::limitCharacters);

        //Controlador de evento para registrar un usuario
        btnSignUp.setOnAction(this::handleSignUp);
        //Controlador de evento para volver a la ventana signIn
        btnBack.setOnAction(this::handleBack);
        //Controlador de evento para cerrar desde la barra de título
        stage.setOnCloseRequest(this::handleCloseRequest);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * Este metodo pretende registrar un nuevo cliente.
     *
     * @param event representa la accion del evento handleSignUp
     */
    private void handleSignUp(ActionEvent event) {
        logger.info("Se ha pulsado el boton SignUp");
        logger.info("Se ha iniciado el metodo para hacer signUp");
        clearLabels();
        if (informedFields()) {
            if (emailPattern() & confirmPassword()) {
                if (isEmailUsernameExists()) {
                    try {
                        Client client = new Client();

                        client.setUsername(txtUsername.getText());
                        client.setEmail(txtEmail.getText());
                        client.setFullName(txtFullName.getText());
                        client.setLastPasswordChange(new Timestamp(System.currentTimeMillis()));
                        client.setPassword(Crypt.encryptAsimetric(txtPassword.getText()));
                        client.setStatus(UserStatus.ENABLED);
                        client.setAccountNumber(0L);
                        client.setIsPremium(false);

                        ci.createClient(client);

                        //Limpiamos todos los campos despues de crear al cliente
                        clearFields();
                        // Alerta para mostrar que ha ido bien
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText("The account has been created successfully");
                        alert.show();
                    } catch (ServerDown ex) {
                        Logger.getLogger(VSignUpController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }

    /**
     * Este metodo pretende limpiar todos los campos.
     */
    private void clearFields() {
        txtUsername.clear();
        txtEmail.clear();
        txtFullName.clear();
        txtPassword.clear();
        txtConfirmPassword.clear();
    }

    /**
     * Este metodo pretende limpiar todos los labels
     */
    private void clearLabels() {
        txtUsername.setStyle(null);
        lblUsername.setVisible(false);
        txtEmail.setStyle(null);
        lblEmail.setVisible(false);
        txtFullName.setStyle(null);
        lblFullName.setVisible(false);
        txtPassword.setStyle(null);
        lblPassword.setVisible(false);
        txtConfirmPassword.setStyle(null);
        lblConfirmPassword.setVisible(false);
    }

    /**
     * Este metodo pretende controlar la entrada de todos los campos de la
     * ventana SignUp para que no se pueda introducir mas de 50 caracteres.
     *
     * @param observable observa el contenido, en este caso el campo escrito
     * @param oldValue valor antiguo del campo
     * @param newValue nuevi valor del campo
     */
    private void limitCharacters(ObservableValue observable, String oldValue,
            String newValue) {
        //logger.info("Inicio del metodo para limitar la entrada de un maximo de caracteres");

        if (txtUsername.getText().length() > 50) {
            txtUsername.setText(oldValue);
        }
        if (txtEmail.getText().length() > 50) {
            txtEmail.setText(oldValue);
        }
        if (txtFullName.getText().length() > 50) {
            txtFullName.setText(oldValue);
        }
        if (txtPassword.getText().length() > 50) {
            txtPassword.setText(oldValue);
        }
        if (txtConfirmPassword.getText().length() > 50) {
            txtConfirmPassword.setText(oldValue);
        }

    }

    /**
     * Este metodo pretende controlar si algun campo de la ventana singUp esta
     * vacio.
     *
     * @return
     */
    private boolean informedFields() {

        logger.info("Se esta comprobando si algun campo esta vacio");

        boolean informed = true;
        if (txtUsername.getText().trim().isEmpty()) {
            txtUsername.setStyle("-fx-border-color: red;");
            lblUsername.setText("Username cannot be empty");
            lblUsername.setVisible(true);
            informed = false;
        }
        if (txtEmail.getText().trim().isEmpty()) {
            txtEmail.setStyle("-fx-border-color: red;");
            lblEmail.setText("Email cannot be empty");
            lblEmail.setVisible(true);
            informed = false;
        }
        if (txtFullName.getText().trim().isEmpty()) {
            txtFullName.setStyle("-fx-border-color: red;");
            lblFullName.setText("Full Name cannot be empty");
            lblFullName.setVisible(true);
            informed = false;
        }
        if (txtPassword.getText().trim().isEmpty()) {
            txtPassword.setStyle("-fx-border-color: red;");
            lblPassword.setText("Password field cannot be empty");
            lblPassword.setVisible(true);
            informed = false;
        }
        if (txtConfirmPassword.getText().trim().isEmpty()) {
            txtConfirmPassword.setStyle("-fx-border-color: red;");
            lblConfirmPassword.setText("Confirm Password field cannot \nbe empty");
            lblConfirmPassword.setVisible(true);
            informed = false;
        }
        return informed;

    }

    /**
     * Este metodo pretende controlar el patron que se puede introducir en el
     * campo email, si es invalido se mostrara una alerta.
     *
     * @return Devuelve el estado del email, false si es invalido y true si es
     * valido
     */
    private boolean emailPattern() {
        //logger.info("Iniciado el evento para controlar si el campo email es valido");

        if (!txtEmail.getText().matches("[\\w.]+@[\\w]+\\.[a-zA-Z]{2,4}")) {
            txtEmail.setStyle("-fx-border-color: red;");
            lblEmail.setText("Only letters, numbers, underscores and\nperiods are allowed, plus a required @");
            lblEmail.setVisible(true);
            return false;
        }
        return true;
    }

    /**
     * Este metodo pretende controlar si los dos campos para escribir la
     * contraseña coinciden.
     *
     * @return Si las contraseñas coinciden devuelve true, si no devuelve false
     * con una alerta indicando el fallo
     */
    private boolean confirmPassword() {
        logger.info("Iniciado el evento para controlar las contraseñas");

        if (!txtPassword.getText().equals(txtConfirmPassword.getText())) {
            txtConfirmPassword.setStyle("-fx-border-color: red;");
            lblConfirmPassword.setText("The entered password is not the same");
            lblConfirmPassword.setVisible(true);
            return false;
        }
        return true;

    }

    /**
     * Este metodo pretende controlar si ya existe el email o username que se ha
     * introducido.
     *
     * @return
     */
    private boolean isEmailUsernameExists() {
        boolean find = true;
        try {
            Collection<User> users = ui.findAllUser();

            if (users.stream().filter(u -> u.getUsername().equals(txtUsername.getText())).count() != 0) {
                txtUsername.setStyle("-fx-border-color: red;");
                lblUsername.setText("Username is already in use");
                lblUsername.setVisible(true);
                find = false;
            }
            if (users.stream().filter(u -> u.getEmail().equals(txtEmail.getText())).count() != 0) {
                txtEmail.setStyle("-fx-border-color: red;");
                lblEmail.setText("Email is already in use");
                lblEmail.setVisible(true);
                find = false;
            }
        } catch (ServerDown ex) {
            Logger.getLogger(VSignUpController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return find;
    }

    /**
     * Este metodo pretende regresar a la ventana singIn mediante la pulsacion
     * del boton back.
     *
     * @param event representa la accion del evento handleBack
     */
    private void handleBack(ActionEvent event) {
        logger.info("Se ha pulsado el boton back");
        logger.info("se volvera a la ventana de VSignIn");
        stage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/kedamosclientside/views/VSignIn.fxml"));

        Parent root = null;
        try {
            root = (Parent) loader.load();
        } catch (IOException ex) {
            logger.severe("Se ha producido un error al cargarel fxml de la ventana singIn");
        }

        VSignInController controller = (VSignInController) loader.getController();
        controller.setStage(stage);
        controller.initStage(root);

    }

    /**
     * Este metodo pretende controlar la salida de la ventana cuando se pulse la
     * X de la barra de titulo.
     *
     * @param event representa la accion del evento handleCloseRequest
     */
    private void handleCloseRequest(WindowEvent event) {
        logger.info("Se ha pulsado la X de la barra de titulo y se enviara un "
                + "aviso de confirmacion");

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("EXIT");

        alert.setContentText("Are you sure you want to go out?");

        Optional<ButtonType> answer = alert.showAndWait();
        if (answer.get() == ButtonType.OK) {
            logger.info("Se ha pulsado OK y el programa va a finalizar");
            stage.close();
        } else {
            logger.info("Se ha cancelado el closeRequest");
            event.consume();
        }

    }

}

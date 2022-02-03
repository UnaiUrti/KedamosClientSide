package kedamosclientside.controllers;

import java.io.IOException;
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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import kedamosclientside.entities.Client;
import kedamosclientside.entities.ClientHolder;
import kedamosclientside.entities.User;
import kedamosclientside.exceptions.PasswordIncorrect;
import kedamosclientside.exceptions.ServerDown;
import kedamosclientside.exceptions.UsernameDoesNotExist;
import kedamosclientside.logic.ClientFactory;
import kedamosclientside.logic.ClientInterface;
import kedamosclientside.logic.UserFactory;
import kedamosclientside.logic.UserInterface;
import kedamosclientside.security.Crypt;

/**
 * Clase controladora para la ventana de SignIn
 *
 * @author Steven Arce
 */
public class VSignInController {

    private Tooltip tooltip;
    private final static Logger logger = Logger.getLogger("kedamosclientside.controllers.VSignInController");

    private Stage stage;
    @FXML
    private Button btnSignIn;
    @FXML
    private Button btnExit;
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Hyperlink hlSignUp;
    @FXML
    private Hyperlink hlForgotPassword;
    @FXML
    private Label lblUsername;
    @FXML
    private Label lblPassword;

    private UserInterface ui = UserFactory.getUserImplementation();
    private ClientInterface ci = ClientFactory.getClientImplementation();
    //private EventManagerInterface emi = EventManagerFactory.getEventManagerImplementation();

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Metodo initStage el cual se va a ejecutar una vez se abra la ventana para
     * inicializarla
     *
     * @param root Nodo del grafo de la escena
     */
    public void initStage(Parent root) {

        logger.info("Iniciado el initStage de la ventana SignIn");

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("SignIn");

        //Campos habilitados
        this.txtUsername.setDisable(false);
        this.txtPassword.setDisable(false);

        //Boton logIn habilitado
        this.btnSignIn.setDisable(false);

        //Hyperlink Not a member? habilitado
        this.hlSignUp.setDisable(false);

        //Hyperlink Forgot password? habilitado
        this.hlForgotPassword.setDisable(false);

        //Se enfoca en el campo username
        this.txtUsername.requestFocus();

        //Ventana no redimensionable
        stage.setResizable(false);

        //Tooltip encima de todos los campos
        tooltip = new Tooltip("Enter yout username");
        this.txtUsername.setTooltip(tooltip);
        tooltip = new Tooltip("Enter your password");
        this.txtPassword.setTooltip(tooltip);

        //Labels no estan visibles
        this.lblUsername.setVisible(false);
        this.lblPassword.setVisible(false);

        //Limitar la entrada de maximo 50 caracteres (ChangeListener)
        this.txtUsername.textProperty().addListener(this::limitCharacters);
        this.txtPassword.textProperty().addListener(this::limitCharacters);

        //Accion de cerrar desde la barra de titulo
        stage.setOnCloseRequest(this::handleCloseRequest);

        stage.show();

    }

    private void clearLabels() {
        txtUsername.setStyle(null);
        lblUsername.setVisible(false);
        txtPassword.setStyle(null);
        lblPassword.setVisible(false);
    }

    /*
    /**
     * Metodo que se va a ejecutar una vez pulsado el boton signIn en el cual se
     * controlan todos los campos y excepciones posibles
     *
     * @param event Respresenta la accion del evento handleSignIn
     */
    @FXML
    private void handleSignInAction(ActionEvent event) {

        logger.info("Se ha pulsado el boton de inicio de sesion");

        clearLabels();

        if (informedFields()) {
            try {
                FXMLLoader loader;
                Parent root;
                // Vamos a comprobar que tipo de usuario se va a logear
                User u = new User();
                u.setUsername(txtUsername.getText().trim());
                u.setPassword(Crypt.encryptAsimetric(txtPassword.getText()));

                Collection<User> users = ui.LoginValidation(u);
                User user = users.stream().findFirst().get();
                //System.out.println("TODO HA SALIDO BIEN");
                switch (user.getPrivilege()) {
                    case CLIENT:
                        // Hacemos un setter a la clase singleton para tener ese cliente en cualquier momento
                        ClientHolder holder = ClientHolder.getInstance();
                        Client client = new Client();
                        client.setUsername(user.getUsername());
                        holder.setClient(ci.getClientByUsername(client));
                        // 
                        stage.close();
                        loader = new FXMLLoader(getClass().getResource("/kedamosclientside/views/VMainMenu.fxml"));
                        root = loader.load();
                        Logger.getLogger(VSignInController.class.getName()).info("Ventana Principal del cliente");
                        VMainMenuController MainMenu = ((VMainMenuController) loader.getController());
                        MainMenu.setStage(stage);
                        MainMenu.initStage(root);
                        break;
                    case EVENT_MANAGER:
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText("The event manager window is under maintenance");
                        alert.show();
                        break;
                    case ADMIN:
                        stage.close();
                        loader = new FXMLLoader(getClass().getResource("/kedamosclientside/views/VUserManagement.fxml"));
                        root = loader.load();
                        Logger.getLogger(VSignInController.class.getName()).info("Ventana Principal del administrador");
                        VUserManagementController UserManagement = ((VUserManagementController) loader.getController());
                        UserManagement.setStage(stage);
                        UserManagement.initStage(root);
                        break;
                }
            } catch (UsernameDoesNotExist ex) {
                txtUsername.setStyle("-fx-border-color: red;");
                lblUsername.setText("Username does not exist");
                lblUsername.setVisible(true);
            } catch (PasswordIncorrect ex) {
                txtPassword.setStyle("-fx-border-color: red;");
                lblPassword.setText("Incorrect password");
                lblPassword.setVisible(true);
            } catch (IOException ex) {
                Logger.getLogger(VSignInController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ServerDown ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(ex.getMessage());
                alert.show();
            }
        }
    }

    /**
     * Metodo tipo booleano que comprueba si los campos estan informados o no
     *
     * @return Devuelve una variable booleana que dice hay algun campo vacio (si
     * es false hay un campo vacio, si es true esta todo informado)
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
        if (txtPassword.getText().trim().isEmpty()) {
            txtPassword.setStyle("-fx-border-color: red;");
            lblPassword.setText("Username field can not be empty");
            lblPassword.setVisible(true);
            informed = false;
        }
        return informed;

    }

    /**
     * Metodo para limitar la entrada de 50 caracteres
     *
     * @param observable
     * @param oldValue
     * @param newValue
     */
    private void limitCharacters(ObservableValue<? extends String> observable, String oldValue,
            String newValue) {
        //logger.info("Inicio del metodo para limitar la entrada de un maximo de caracteres");

        if (txtUsername.getText().length() > 50) {
            txtUsername.setText(oldValue);
        }
        if (txtPassword.getText().length() > 50) {
            txtPassword.setText(oldValue);
        }

    }

    /**
     * Metodo que se va a ejecutar una vez pulsado el hyperlink de signUp el
     * cual te va a llevar a la ventana SignUp
     *
     * @param event Respresenta la accion del evento handleSignUp
     * @throws IOException Excepcion requerida para el load()
     */
    @FXML
    private void handleSignUpAction(ActionEvent event) {

        try {
            logger.info("Se ha pulsado el hyperlink para ir a la ventana de registro");

            //La ventana actual se cierra
            stage.close();
            //El usuario es dirigido a la ventana SignUp
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/kedamosclientside/views/VSignUp.fxml"));
            Parent root = loader.load();
            VSignUpController controller = ((VSignUpController) loader.getController());
            controller.setStage(stage);
            controller.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(VSignInController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Metodo que se va a ejecutar una vez pulsado el hyperlink de
     * ForgotPassword el cual te va a llevar a la ventana ResetPassword
     *
     * @param event
     */
    @FXML
    private void handleForgotPasswdAction(ActionEvent event) {

        try {
            //La ventana actual se cierra
            stage.close();
            //El usuario inicia sesion y va a la ventana LogOut
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/kedamosclientside/views/VResetPassword.fxml"));
            Parent root = loader.load();
            VResetPasswordController controller = ((VResetPasswordController) loader.getController());
            controller.setStage(stage);
            controller.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(VSignInController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Metodo que se ejecutara una vez pulsado el boton exit el cual va a
     * confirmar si de verdad quiere salir
     *
     * @param event Respresenta la accion del evento handleExit
     */
    @FXML
    private void handleExitAction(ActionEvent event) {

        logger.info("Se ha pulsado el boton EXIT y se enviara un aviso de "
                + "confirmacion al usuario");

        //Se envia un aviso al usuario confirmando si de verdad quiere salir
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("EXIT");

        alert.setContentText("Are you sure you want to go out?");

        Optional<ButtonType> answer = alert.showAndWait();
        if (answer.get() == ButtonType.OK) {
            logger.info("Se ha pulsado OK y el programa va a finalizar");

            //El programa finalizara
            stage.close();
        } else {
            logger.info("Se ha pulsado CANCELAR y el evento va a ser cancelado");

            //El aviso se cierra y el usuario continua en la ventana
            event.consume();
        }

    }

    /**
     * Metodo que se va a ejecutar una vez pulsado el boton X de la barra de
     * titulo de la aplicacion el cual va a confirmar si de verdad quiere salir
     *
     * @param event Respresenta la accion del evento handleCloseRequest
     */
    private void handleCloseRequest(WindowEvent event) {

        logger.info("Se ha pulsado la X de la barra de titulo y se enviara "
                + "un aviso de confirmacion al usuario");

        //Se envia un mensaje al usuario confirmando si de verdad quiere salir
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("EXIT");

        alert.setContentText("Are you sure you want to go out?");

        Optional<ButtonType> answer = alert.showAndWait();
        if (answer.get() == ButtonType.OK) {
            logger.info("Se ha pulsado OK y el programa va a finalizar");

            //El programa finalizara
            stage.close();
        } else {
            logger.info("Se ha pulsado CANCELAR y el evento va a ser cancelado");

            //El aviso se cierra y el usuario continua en la ventana
            event.consume();
        }

    }

}

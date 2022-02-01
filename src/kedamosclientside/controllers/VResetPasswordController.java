package kedamosclientside.controllers;

import java.io.IOException;
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
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import kedamosclientside.entities.Client;
import kedamosclientside.exceptions.EmailDoesNotExist;
import kedamosclientside.exceptions.ServerDown;
import kedamosclientside.logic.ClientFactory;
import kedamosclientside.logic.ClientInterface;

/**
 * Clase controladora para la ventana de SignIn
 *
 * @author Steven Arce
 */
public class VResetPasswordController {

    private Tooltip tooltip;
    //private final static Logger logger = Logger.getLogger("kedamosclientside.controllers.VResetPasswordController");

    private Stage stage;
    @FXML
    private TextField txtEmail;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnResetPassword;
    @FXML
    private ImageView imgPassIco;
    @FXML
    private Label lblSignIn;
    @FXML
    private Label lblEmail;

    private ClientInterface ci = ClientFactory.getClientImplementation();

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

        //logger.info("Iniciado el initStage de la ventana ResetPassword");
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("ResetPassword");

        this.txtEmail.setDisable(false);

        //Ventana no redimensionable
        stage.setResizable(false);

        //Accion de cerrar desde la barra de titulo
        stage.setOnCloseRequest(this::handleCloseRequest);

        // Se enfoca en el campo email
        txtEmail.requestFocus();

        // Limtar la entrada de caracteres
        this.txtEmail.textProperty().addListener(this::limitCharacters);
        
        //Accion de cerrar desde la barra de titulo
        stage.setOnCloseRequest(this::handleCloseRequest);
        
        stage.show();

    }

    @FXML
    private void handleResetPasswordAction(ActionEvent event) {
        try {
            lblEmail.setVisible(false);
            txtEmail.setStyle(null);
            Client client = new Client();
            client.setEmail(txtEmail.getText());
            ci.resetPassword(client);
            txtEmail.clear();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Password reset successfully");
            alert.show();
        } catch (EmailDoesNotExist ex) {
            txtEmail.setStyle("-fx-border-color: red;");
            lblEmail.setText(ex.getMessage());
            lblEmail.setVisible(true);
        } catch (ServerDown ex) {
            Logger.getLogger(VResetPasswordController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void limitCharacters(ObservableValue observable, String oldValue,
            String newValue) {
        //logger.info("Inicio del metodo para limitar la entrada de un maximo de caracteres");

        if (txtEmail.getText().length() > 50) {
            txtEmail.setText(oldValue);
        }

    }

    @FXML
    private void handleBack(ActionEvent event) {
        //logger.info("Se ha pulsado el boton back");
        //logger.info("se volvera a la ventana de VSignIn");
        stage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/kedamosclientside/views/VSignIn.fxml"));

        Parent root = null;
        try {
            root = (Parent) loader.load();
        } catch (IOException ex) {
            //logger.severe("Se ha producido un error al cargarel fxml de la ventana singIn");
        }

        VSignInController controller = (VSignInController) loader.getController();
        controller.setStage(stage);
        controller.initStage(root);

    }

    private void handleCloseRequest(WindowEvent event) {

        //logger.info("Se ha pulsado la X de la barra de titulo y se enviara "
        //        + "un aviso de confirmacion al usuario");
        //Se envia un mensaje al usuario confirmando si de verdad quiere salir
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("EXIT");

        alert.setContentText("Are you sure you want to go out?");

        Optional<ButtonType> answer = alert.showAndWait();
        if (answer.get() == ButtonType.OK) {
            //logger.info("Se ha pulsado OK y el programa va a finalizar");

            //El programa finalizara
            stage.close();
        } else {
            //logger.info("Se ha pulsado CANCELAR y el evento va a ser cancelado");

            //El aviso se cierra y el usuario continua en la ventana
            event.consume();
        }

    }

}

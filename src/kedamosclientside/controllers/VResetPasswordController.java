package kedamosclientside.controllers;

import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import kedamosclientside.entities.Client;
import kedamosclientside.logic.ClientFactory;

/**
 * Clase controladora para la ventana de SignIn
 *
 * @author UnaiUrtiaga, AdrianFranco
 */
public class VResetPasswordController {

    private Tooltip tooltip;
    //private final static Logger logger = Logger.getLogger("kedamosclientside.controllers.VResetPasswordController");

    private Stage stage;
    @FXML
    private TextField txtEmail;
    @FXML
    private Button bntResetPassword;
    @FXML
    private Button btnExit;

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

        //Accion de cerrar desde la barra de titulo
        //stage.setOnCloseRequest(this::handleCloseRequest);
        stage.show();

    }

    @FXML
    private void handleResetPasswordAction(ActionEvent event) {
        
        Client client = new Client();
        client.setEmail(txtEmail.getText());

        ClientFactory.getClientImplementation().resetPassword(client);
       
    }

    @FXML
    private void handleCloseRequest(WindowEvent event) {

        //logger.info("Se ha pulsado la X de la barra de titulo y se enviara "
        //        + "un aviso de confirmacion al usuario");

        //Se envia un mensaje al usuario confirmando si de verdad quiere salir
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("EXIT");

        alert.setContentText("Seguro que quiere salir?");

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

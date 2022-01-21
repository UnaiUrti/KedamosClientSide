package kedamosclientside.controllers;

import java.io.IOException;
import java.util.Optional;
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
import kedamosclientside.entities.UserBean;

/**
 * Clase controladora para la ventana de SignIn
 *
 * @author UnaiUrtiaga, AdrianFranco
 */
public class VResetPasswordController {

    private Tooltip tooltip;
    private final static Logger logger = Logger.getLogger("client.controllers.VSignInController");

    private Stage stage;
    @FXML
    private TextField txtEmail;


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

        logger.info("Iniciado el initStage de la ventana ResetPasswors");

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("ResetPassword");

        this.txtEmail.setDisable(false);
        
        //Ventana no redimensionable
        stage.setResizable(false);

        //Accion de cerrar desde la barra de titulo
        //stage.setOnCloseRequest(this::handleCloseRequest);

        stage.show();

    }
        

}

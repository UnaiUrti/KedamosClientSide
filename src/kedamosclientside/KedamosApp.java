/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kedamosclientside;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import kedamosclientside.controllers.VSignInController;

/**
 *
 * @author 2dam
 */
public class KedamosApp extends Application {
    
        /**
     * Metodo main que va a ejecutar el metodo start
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Metodo start para abrir la primera ventana de la aplicacion
     * @param primaryStage Parametro de tipo Stage el cual se va a utilizar para
     * darle un nuevo stage al controlador
     * @throws Exception Un controlador para la excepcion del load()
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/kedamosclientside/views/VSignIn.fxml"));
        
        Parent root = loader.load();
        
        VSignInController controller = ((VSignInController)loader.getController());
        controller.setStage(primaryStage);
        controller.initStage(root);
        
    }
    
}

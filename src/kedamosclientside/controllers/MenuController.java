/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kedamosclientside.controllers;

import java.io.IOException;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.application.Platform;

/**
 *
 * @author Steven Arce
 */
public class MenuController {

    @FXML
    private MenuBar menuBar;
    @FXML
    private MenuItem miMyProfile;
    @FXML
    private MenuItem miMyEvents;
    @FXML
    private MenuItem miMyComments;
    @FXML
    private MenuItem miLogOut;
    @FXML
    private HBox hbMenu;
    @FXML
    private MenuItem miHome;

    private Stage stage;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    /*
    public void initStage(Parent root) {

        //logger.info("Iniciado el initStage de la ventana ResetPassword");
        Scene scene = new Scene(root);

        stage.setScene(scene);
        //stage.setTitle("");
        //Ventana no redimensionable
        stage.setResizable(false);

        stage.show();

    }
    */
    
    @FXML
    private void handleMyProfile(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/kedamosclientside/views/VMyProfile.fxml"));
            Parent root = loader.load();
            Logger.getLogger(VSignInController.class.getName()).info("");
            VMyProfileController controller = ((VMyProfileController) loader.getController());
            controller.setStage((Stage) hbMenu.getScene().getWindow());
            controller.initStage(root);
        } catch (IOException ex) {
            //LOG.log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void handleHome(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/kedamosclientside/views/VMainMenu.fxml"));
            Parent root = loader.load();
            Logger.getLogger(VSignInController.class.getName()).info("");
            VMainMenuController controller = ((VMainMenuController) loader.getController());
            controller.setStage((Stage) hbMenu.getScene().getWindow());
            controller.initStage(root);
        } catch (IOException ex) {
            //LOG.log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleLogOut(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/kedamosclientside/views/VSignIn.fxml"));
            Parent root = (Parent) loader.load();
            VSignInController controller = (VSignInController) loader.getController();
            controller.setStage((Stage) hbMenu.getScene().getWindow());
            controller.initStage(root);
        } catch (IOException ex) {
            //logger.severe("Se ha producido un error al cargarel fxml de la ventana singIn");
        }

    }

}

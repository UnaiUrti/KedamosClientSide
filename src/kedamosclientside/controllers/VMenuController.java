/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kedamosclientside.controllers;

import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import kedamosclientside.entities.Event;

/**
 *
 * @author Irkus
 */
public class VMenuController {

    private final static Logger logger = Logger.getLogger("client.controllers.VSignUpController");
    private Stage stage;
    @FXML
    private MenuItem mainMenu;
    @FXML
    private MenuItem viewEvents;
    @FXML
    private MenuItem myProfile;
    @FXML
    private MenuItem myEvents;
    @FXML
    private MenuItem myComments;
    @FXML
    private MenuItem logOut;

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
     * @param id
     */
    public void initStage(Parent root, Event id) {
        logger.info("Iniciado initStage de la ventana PersonalResource");
        Scene scene = new Scene(root);
        mainMenu.setOnAction(this::handleMainMenu);
        
        viewEvents.setOnAction(this::handleViewEvents);

        myEvents.setOnAction(this::handleMyEvents);
        myProfile.setOnAction(this::handleMyProfile);
        myComments.setOnAction(this::handleMyComments);
        logOut.setOnAction(this::handleLogOut);

        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void handleMainMenu(ActionEvent action){
        logger.info("MainMenu");
    }
    @FXML 
    private void handleViewEvents(ActionEvent action){
         /* FXMLLoader loader = new FXMLLoader(getClass().getResource("/kedamosclientside/views/VEvent.fxml"));
                    Parent root = loader.load();                             
                    VEventController controller = ((VEventController) loader.getController());
          
                    controller.setStage( (Stage)hbMenuAdm.getScene().getWindow(););
                    controller.initStage(root);*/
        logger.info("viewEvents");
    }
    @FXML
    private void handleMyEvents(ActionEvent action){
         /*FXMLLoader loader = new FXMLLoader(getClass().getResource("/kedamosclientside/views/VEvent.fxml"));
                    Parent root = loader.load();   
                    Client cli=new Client();
                    VEventController controller = ((VEventController) loader.getController());
                     controller.setStage( (Stage)hbMenuAdm.getScene().getWindow(););
                    controller.initStage(root,per);*/
        logger.info("MyEvents");
    }
    @FXML
    private void handleMyProfile(ActionEvent action){
         /*FXMLLoader loader = new FXMLLoader(getClass().getResource("/kedamosclientside/views/VProfile.fxml"));
                    Parent root = loader.load();                             
                    VProfileController controller = ((VProfileController) loader.getController());
                    controller.setStage( (Stage)hbMenuAdm.getScene().getWindow(););
                    controller.initStage(root);*/
            logger.info("MyProfile");
        
    }
    @FXML
    private void handleMyComments(ActionEvent action){
         /* FXMLLoader loader = new FXMLLoader(getClass().getResource("/kedamosclientside/views/VComment.fxml"));
                    Parent root = loader.load();                             
                    VCommentController controller = ((VCommentController) loader.getController());
                     controller.setStage( (Stage)hbMenuAdm.getScene().getWindow(););
                    controller.initStage(root);*/
            logger.info("MyComents");
      
    }
    @FXML
    private void handleLogOut(ActionEvent action){
     /*
             FXMLLoader loader = new FXMLLoader(getClass().getResource("/kedamosclientside/views/VLogin.fxml"));
                    Parent root = loader.load();                             
                    VLoginController controller = ((VLoginController) loader.getController());
                    controller.setStage(stage); controller.setStage( (Stage)hbMenuAdm.getScene().getWindow(););
                    controller.initStage(root);*/
            logger.info("logOut");

    }
}

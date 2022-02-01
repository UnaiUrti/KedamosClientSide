/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kedamosclientside;

import java.util.Scanner;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import kedamosclientside.controllers.MyEventsViewController;
import kedamosclientside.logic.EventFactory;

/**
 *
 * @author Adrian Franco
 */
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        FXMLLoader loader;
        loader = new FXMLLoader(getClass().getResource("/kedamosclientside/views/MyEventsView.fxml"));
        
        Parent root = (Parent) loader.load();
        
        MyEventsViewController controller= ((MyEventsViewController)loader.getController());
        controller.setEventinterface(EventFactory.getEvent());
        controller.setStage(primaryStage);
        controller.initStage(root, null);
        
    }

}
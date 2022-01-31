/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kedamosclientside.controllers;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Steven Arce
 */
public class VMainMenuController {

    private Stage stage;
    
    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initStage(Parent root) {

        //logger.info("Iniciado el initStage de la ventana ResetPassword");
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("MainMenu");

        //Ventana no redimensionable
        stage.setResizable(false);

        stage.show();

    }
    
}

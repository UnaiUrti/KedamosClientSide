/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kedamosclientside.controllers;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import kedamosclientside.entities.Client;
import kedamosclientside.entities.ClientHolder;
import kedamosclientside.exceptions.PasswordIncorrect;
import kedamosclientside.logic.ClientFactory;
import kedamosclientside.logic.ClientInterface;
import kedamosclientside.logic.UserFactory;
import kedamosclientside.logic.UserInterface;
import kedamosclientside.security.Crypt;

/**
 *
 * @author Steven Arce
 */
public class VMyProfileController {

    @FXML
    private Pane signInPane;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtFullName;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtAccountNumber;
    @FXML
    private Button bntSave;
    @FXML
    private ComboBox cbPremium;
    @FXML
    private Label lblEmail;
    @FXML
    private Label lblAccountNumber;
    @FXML
    private Label lblFullName;
    @FXML
    private Label lblPremium;
    @FXML
    private Label lblUsername;
    @FXML
    private Button bntSaveChangePassword;
    @FXML
    private PasswordField txtNewPassword;
    @FXML
    private PasswordField txtConfirmNewPassword;
    @FXML
    private PasswordField txtCurrentPassword;
    @FXML
    private Label lblCurrentPassword;
    @FXML
    private Label lblNewPassword;
    @FXML
    private Label lblConfirmNewPassword;
    @FXML
    private Button bntDeleteAccount;

    private Stage stage;

    private UserInterface ui = UserFactory.getUserImplementation();
    private ClientInterface ci = ClientFactory.getClientImplementation();
    private Client holder = ClientHolder.getInstance().getClient();

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
        stage.setTitle("MyProfile");

        //Ventana no redimensionable
        stage.setResizable(false);

        cbPremium.getItems().addAll("Yes", "No");

        setClientData();

        stage.show();

    }

    private void setClientData() {

        ClientHolder clientHolder;
        clientHolder = ClientHolder.getInstance();
        Client c = clientHolder.getClient();

        txtEmail.setText(c.getEmail());
        txtFullName.setText(c.getFullName());
        txtUsername.setText(c.getUsername());
        txtAccountNumber.setText(c.getAccountNumber().toString());
        if (c.isIsPremium()) {
            cbPremium.setValue("Yes");
        } else {
            cbPremium.setValue("No");
        }

    }

    private void clearLabelsChangePassword() {
        lblCurrentPassword.setVisible(false);
        lblNewPassword.setVisible(false);
        lblConfirmNewPassword.setVisible(false);
    }

    private boolean informedChangePassword() {

        boolean informed = true;
        if (txtCurrentPassword.getText().isEmpty()) {
            lblCurrentPassword.setVisible(true);
            lblCurrentPassword.setText("Current Password cannot be empty");
            txtCurrentPassword.setStyle("-fx-border-color: red;");
            informed = false;
        }
        if (txtNewPassword.getText().isEmpty()) {
            lblNewPassword.setVisible(true);
            lblNewPassword.setText("New Password cannot be empty");
            txtNewPassword.setStyle("-fx-border-color: red;");
            informed = false;
        }
        if (txtConfirmNewPassword.getText().isEmpty()) {
            lblConfirmNewPassword.setVisible(true);
            lblConfirmNewPassword.setText("Confirm New Password cannot be empty");
            txtConfirmNewPassword.setStyle("-fx-border-color: red;");
            informed = false;
        }
        return informed;
    }

    private boolean confirmPassword() {
        //
        if (!txtNewPassword.getText().equals(txtConfirmNewPassword.getText())) {
            lblConfirmNewPassword.setVisible(true);
            lblConfirmNewPassword.setText("Entered passwords do not match");
            txtConfirmNewPassword.setStyle("-fx-border-color: red;");
            return false;
        }
        return true;

    }

    private boolean validatePassword() {
        try {
            Client c = new Client();
            c.setUsername(holder.getUsername());
            c.setPassword(Crypt.encryptAsimetric(txtNewPassword.getText()));
            ci.validatePassword(c);
            return true;
        } catch (PasswordIncorrect ex) {
            lblCurrentPassword.setVisible(true);
            lblCurrentPassword.setText("Current password does not match");
            txtCurrentPassword.setStyle("-fx-border-color: red;");
            return false;
        }

    }

    @FXML
    private void handleSaveUserInformation(ActionEvent event) {
        
        
        
    }

    @FXML
    private void handleChangePassword(ActionEvent event) {
        clearLabelsChangePassword();
        if (informedChangePassword()) {
            if (confirmPassword()) {
                if (validatePassword()) {
                    holder.setPassword(Crypt.encryptAsimetric(txtNewPassword.getText()));
                    ci.changePassword(holder);
                }
            }
        }

    }

    @FXML
    private void handleDeleteAccount(ActionEvent event) {

        try {
            ci.removeClient(holder);

            stage.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/kedamosclientside/views/VSignIn.fxml"));
            Parent root = (Parent) loader.load();
            VSignInController controller = (VSignInController) loader.getController();
            controller.setStage(stage);
            controller.initStage(root);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("The account has been deleted successfully");
            alert.show();

        } catch (IOException ex) {
            Logger.getLogger(VMyProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}

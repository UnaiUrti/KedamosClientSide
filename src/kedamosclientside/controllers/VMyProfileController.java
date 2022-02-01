/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kedamosclientside.controllers;

import java.io.IOException;
import java.util.Collection;
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
import kedamosclientside.entities.User;
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
    private Button btnSave;
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
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnCancel;

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

        // Desabilitar el boton save
        btnSave.setDisable(true);
        // No dejar editar los datos hasta darle al boton edit
        txtEmail.setEditable(false);
        txtFullName.setEditable(false);
        txtUsername.setEditable(false);
        txtAccountNumber.setEditable(false);
        cbPremium.setDisable(true);

        // Insertar la informacion del cliente en los fields
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

        txtCurrentPassword.setStyle(null);
        txtNewPassword.setStyle(null);
        txtConfirmNewPassword.setStyle(null);

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
            c.setPassword(Crypt.encryptAsimetric(txtCurrentPassword.getText()));
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
    private void handleEditUserInformation(ActionEvent event) {

        btnSave.setDisable(false);
        btnCancel.setDisable(false);
        btnEdit.setDisable(true);

        txtEmail.setEditable(true);
        txtFullName.setEditable(true);
        txtUsername.setEditable(true);
        txtAccountNumber.setEditable(true);
        cbPremium.setDisable(false);
    }

    @FXML
    private void handleCancelUserInformation(ActionEvent event) {

        btnSave.setDisable(true);
        btnCancel.setDisable(true);
        btnEdit.setDisable(false);

        txtEmail.setEditable(false);
        txtFullName.setEditable(false);
        txtUsername.setEditable(false);
        txtAccountNumber.setEditable(false);
        cbPremium.setDisable(true);

        setClientData();

    }

    @FXML
    private void handleSaveUserInformation(ActionEvent event) {
        clearLabelsSaveUserInformation();
        if (informedSaveUserInformation()) {
            if (isEmailUsernameExists()) {
                holder.setEmail(txtEmail.getText());
                holder.setFullName(txtFullName.getText());
                holder.setUsername(txtUsername.getText());
                holder.setAccountNumber(Long.parseLong(txtAccountNumber.getText()));
                if (cbPremium.getValue().equals("Yes")) {
                    holder.setIsPremium(true);
                } else {
                    holder.setIsPremium(false);
                }
                ci.editClient(holder);
                btnSave.setDisable(true);
                btnCancel.setDisable(true);
                btnEdit.setDisable(false);

                txtEmail.setEditable(false);
                txtFullName.setEditable(false);
                txtUsername.setEditable(false);
                txtAccountNumber.setEditable(false);
                cbPremium.setDisable(true);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Your data has been changed successfully");
                alert.show();
            }
        }

    }

    private void clearLabelsSaveUserInformation() {

        lblEmail.setVisible(false);
        lblAccountNumber.setVisible(false);
        lblFullName.setVisible(false);
        lblPremium.setVisible(false);
        lblUsername.setVisible(false);

    }

    private boolean informedSaveUserInformation() {

        boolean informed = true;
        if (txtEmail.getText().isEmpty()) {
            lblEmail.setVisible(true);
            lblEmail.setText("Email cannot be empty");
            txtEmail.setStyle("-fx-border-color: red;");
            informed = false;
        }
        if (txtFullName.getText().isEmpty()) {
            lblFullName.setVisible(true);
            lblFullName.setText("Full name cannot be empty");
            txtFullName.setStyle("-fx-border-color: red;");
            informed = false;
        }
        if (txtUsername.getText().isEmpty()) {
            lblUsername.setVisible(true);
            lblUsername.setText("Username cannot be empty");
            txtUsername.setStyle("-fx-border-color: red;");
            informed = false;
        }
        if (txtAccountNumber.getText().isEmpty()) {
            lblAccountNumber.setVisible(true);
            lblAccountNumber.setText("Account number cannot be empty");
            txtAccountNumber.setStyle("-fx-border-color: red;");
            informed = false;
        }
        if (cbPremium.getSelectionModel().getSelectedIndex() == -1) {
            lblPremium.setVisible(true);
            lblPremium.setText("Premium cannot be empty");
            cbPremium.setStyle("-fx-border-color: red;");
            informed = false;
        }
        return informed;
    }

    private boolean isEmailUsernameExists() {

        boolean find = true;
        Collection<User> users = ui.findAllUser();

        if (users.stream().filter(u -> u.getUsername().equals(txtUsername.getText())).
                count() != 0 & !holder.getUsername().equalsIgnoreCase(txtUsername.getText().trim())) {
            txtUsername.setStyle("-fx-border-color: red;");
            lblUsername.setText("Username is already in use");
            lblUsername.setVisible(true);
            find = false;
        }
        if (users.stream().filter(u -> u.getEmail().equals(txtEmail.getText()))
                .count() != 0 & !holder.getEmail().equalsIgnoreCase(txtEmail.getText().trim())) {
            txtEmail.setStyle("-fx-border-color: red;");
            lblEmail.setText("Email is already in use");
            lblEmail.setVisible(true);
            find = false;
        }
        return find;
    }

    @FXML
    private void handleChangePassword(ActionEvent event) {
        clearLabelsChangePassword();
        if (informedChangePassword()) {
            if (confirmPassword()) {
                if (validatePassword()) {
                    holder.setPassword(Crypt.encryptAsimetric(txtNewPassword.getText()));
                    ci.changePassword(holder);
                    clearLabelsChangePassword();
                    clealAllChangePasswordFields();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("The password has been changed successfully");
                    alert.show();
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

    private void clealAllChangePasswordFields() {
        txtCurrentPassword.clear();
        txtNewPassword.clear();
        txtConfirmNewPassword.clear();
    }

}

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
import javafx.beans.value.ObservableValue;
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
import kedamosclientside.exceptions.ServerDown;
import kedamosclientside.logic.ClientFactory;
import kedamosclientside.logic.ClientInterface;
import kedamosclientside.logic.UserFactory;
import kedamosclientside.logic.UserInterface;
import kedamosclientside.security.Crypt;

/**
 * Esta clase represemta el controlador de la ventana VMyProfile
 *
 * @author Steven Arce
 */
public class VMyProfileController {

    private final static Logger logger = Logger.getLogger("kedamosclientside.controllers.VMyProfileController");

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

    /**
     * Metodo initStage el cual se va a ejecutar una vez se abra la ventana para
     * inicializarla
     *
     * @param root Nodo del grafo de la escena
     */
    public void initStage(Parent root) {

        logger.info("Iniciado el initStage de la ventana VMyProfile");
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

        // Limitar la entrada de caracteres
        txtUsername.textProperty().addListener(this::limitCharacters);
        txtEmail.textProperty().addListener(this::limitCharacters);
        txtFullName.textProperty().addListener(this::limitCharacters);
        txtAccountNumber.textProperty().addListener(this::limitCharacters);
        txtCurrentPassword.textProperty().addListener(this::limitCharacters);
        txtNewPassword.textProperty().addListener(this::limitCharacters);
        txtConfirmNewPassword.textProperty().addListener(this::limitCharacters);

        // Insertar la informacion del cliente en los fields
        setClientData();

        stage.show();

    }

    /**
     * Este metodo carga los campos de la ventana con los datos del cliente que
     * ha iniciado sesion.
     */
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
        logger.info("Se ha cargado los datos del cliente actual");
    }

    /**
     * Este metodo pretende hacer invisible los labels y devolver el color
     * original a los bordes de los campos.
     */
    private void clearLabelsChangePassword() {

        txtCurrentPassword.setStyle(null);
        txtNewPassword.setStyle(null);
        txtConfirmNewPassword.setStyle(null);

        lblCurrentPassword.setVisible(false);
        lblNewPassword.setVisible(false);
        lblConfirmNewPassword.setVisible(false);
        logger.info("Se ha cargado los datos del cliente actual");
    }

    /**
     * Este metodo valida si los campos estan informados.
     *
     * @return Retorna true si los campos estan informados.
     */
    private boolean informedChangePassword() {
        logger.info("Se ha inicado la validacion de los campos informados");
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

    /**
     * Este metodo pretende validar que la nueva contraseña introducida este
     * repetida correctamente.
     *
     * @return Retorna true si la nueva contraseña esta bien introducida.
     */
    private boolean confirmPassword() {
        logger.info("Se ha inicado la validacion de la nueva contraseña");
        if (!txtNewPassword.getText().equals(txtConfirmNewPassword.getText())) {
            lblConfirmNewPassword.setVisible(true);
            lblConfirmNewPassword.setText("Entered passwords do not match");
            txtConfirmNewPassword.setStyle("-fx-border-color: red;");
            return false;
        }
        return true;

    }

    /**
     * Este metodo pretende validar que la contraseña actual sea la misma que la
     * que esta en la base de datos.
     *
     * @return Retorna true si coinciden.
     */
    private boolean validatePassword() {
        logger.info("Se ha inicado la validacion de la nueva contraseña");
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
        } catch (ServerDown ex) {
            logger.severe("Error el servidor esta caido o apagado");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(ex.getMessage());
            alert.show();
        }
        return false;
    }

    /**
     * Este metodo pretende habilitar los campos y los botones save y cancenl
     * cuando se presione el boton edit.
     *
     * @param event Representa la accion del evento handleEditUserInformation.
     */
    @FXML
    private void handleEditUserInformation(ActionEvent event) {

        logger.info("Se ha pulsado el boton edit");
        btnSave.setDisable(false);
        btnCancel.setDisable(false);
        btnEdit.setDisable(true);

        txtEmail.setEditable(true);
        txtFullName.setEditable(true);
        txtUsername.setEditable(true);
        txtAccountNumber.setEditable(true);
        cbPremium.setDisable(false);
    }

    /**
     * Este metodo pretende cancelar la edicion del cliente cuando se pulse el
     * boton cancel
     *
     * @param event Representa la accion del evento handleCancelUserInformation.
     */
    @FXML
    private void handleCancelUserInformation(ActionEvent event) {
        logger.info("Se ha pulsado el boton cancel");
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

    /**
     * Este metodo pretende guardar la informacion modificada del cliente.
     *
     * @param event Representa la accion del evento handleSaveUserInformation.
     */
    @FXML
    private void handleSaveUserInformation(ActionEvent event) {
        logger.info("Se ha pulsado el boton save");
        clearLabelsSaveUserInformation();
        if (informedSaveUserInformation()) {
            if (isEmailUsernameExists()) {
                try {
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
                } catch (ServerDown ex) {
                    logger.severe("Error el servidor esta caido o apagado");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(ex.getMessage());
                    alert.show();
                }
            }
        }

    }

    /**
     * Este metodo pretende hacer invisibles los labels de la informacion del
     * cliente.
     */
    private void clearLabelsSaveUserInformation() {

        lblEmail.setVisible(false);
        lblAccountNumber.setVisible(false);
        lblFullName.setVisible(false);
        lblPremium.setVisible(false);
        lblUsername.setVisible(false);
        logger.info("Se ha hecho invisibles los labels");
    }

    /**
     * Este metodo pretende validar que los campos de la informacion del cliente
     * este informado.
     *
     * @return Retorna true si los campos estan informados.
     */
    private boolean informedSaveUserInformation() {
        logger.info("Se ha iniciado la validacion de los campos informados");
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

    /**
     * Este metodo valida que al editar un cliente el usuario y el email no
     * exista en la base de datos.
     *
     * @return Retorna true si no existe el usuario y el email.
     */
    private boolean isEmailUsernameExists() {
        logger.info("Se ha iniciado la validacion del usuario y email existente");
        boolean find = true;
        try {
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
        } catch (ServerDown ex) {
            logger.severe("Error el servidor esta caido o apagado");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(ex.getMessage());
            alert.show();
        }
        return find;
    }

    private void limitCharacters(ObservableValue observable, String oldValue,
            String newValue) {
        //logger.info("Inicio del metodo para limitar la entrada de un maximo de caracteres");
        if (txtUsername.getText().length() > 50) {
            txtUsername.setText(oldValue);
        }
        if (txtFullName.getText().length() > 50) {
            txtFullName.setText(oldValue);
        }
        if (txtEmail.getText().length() > 50) {
            txtEmail.setText(oldValue);
        }
        if (txtAccountNumber.getText().length() > 50) {
            txtAccountNumber.setText(oldValue);
        }
        if (txtCurrentPassword.getText().length() > 50) {
            txtCurrentPassword.setText(oldValue);
        }
        if (txtNewPassword.getText().length() > 50) {
            txtNewPassword.setText(oldValue);
        }
        if (txtConfirmNewPassword.getText().length() > 50) {
            txtConfirmNewPassword.setText(oldValue);
        }
        if (!txtAccountNumber.getText().matches("\\d*")) {
            txtAccountNumber.setText(oldValue);
        }

    }

    /**
     * Este metodo pretende cambiar la contraseña del cliente.
     *
     * @param event Representa la accion del evento handleChangePassword.
     */
    @FXML
    private void handleChangePassword(ActionEvent event) {
        logger.info("Se ha iniciado la validacion para cambiar la contraseña");
        clearLabelsChangePassword();
        if (informedChangePassword()) {
            if (confirmPassword()) {
                if (validatePassword()) {
                    try {
                        holder.setPassword(Crypt.encryptAsimetric(txtNewPassword.getText()));
                        ci.changePassword(holder);
                        clearLabelsChangePassword();
                        clealAllChangePasswordFields();
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText("The password has been changed successfully");
                        alert.show();
                    } catch (ServerDown ex) {
                        logger.severe("Error el servidor esta caido o apagado");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText(ex.getMessage());
                        alert.show();
                    }
                }
            }
        }

    }

    /**
     * Este metodo pretende eliminar el actual cliente.
     *
     * @param event Representa la accion del evento handleDeleteAccount.
     */
    @FXML
    private void handleDeleteAccount(ActionEvent event) {
        logger.info("Se ha iniciado el evento para eliminar el cliente");
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
        } catch (ServerDown ex) {
            logger.severe("Error el servidor esta caido o apagado");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(ex.getMessage());
            alert.show();
        }

    }

    /**
     * Este metodo pretende limpiar los campos de la contraseña.
     */
    private void clealAllChangePasswordFields() {
        txtCurrentPassword.clear();
        txtNewPassword.clear();
        txtConfirmNewPassword.clear();
        logger.info("Se ha limpiado los campos de passowrd");
    }

}

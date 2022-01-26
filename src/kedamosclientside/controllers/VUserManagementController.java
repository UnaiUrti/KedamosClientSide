package kedamosclientside.controllers;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import kedamosclientside.entities.EventManager;
import kedamosclientside.entities.Type;
import kedamosclientside.entities.User;
import kedamosclientside.entities.UserStatus;
import kedamosclientside.logic.EventManagerFactory;
import kedamosclientside.logic.EventManagerInterface;
import org.glassfish.jersey.server.monitoring.RequestEvent;

/**
 *
 *
 * @author Steven Arce
 */
public class VUserManagementController {

    //private Tooltip tooltip;
    //private final static Logger logger = Logger.getLogger("kedamosclientside.controllers.VResetPasswordController");
    private Stage stage;
    @FXML
    private TableView tlView;

    private ObservableList<EventManager> usersData;

    private EventManagerInterface emi = EventManagerFactory.getEventManagerImplementation();

    @FXML
    private TableColumn tlEmail;
    @FXML
    private TableColumn tlFullName;
    @FXML
    private TableColumn tlLastPasswordChange;
    @FXML
    private TableColumn tlStatus;
    @FXML
    private TableColumn tlUsername;
    @FXML
    private TableColumn tlManagetCategory;
    @FXML
    private ComboBox cbStatus;
    @FXML
    private ComboBox cbManagerCategory;
    @FXML
    private Button btnCreate;
    @FXML
    private Button btnModify;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnPrint;
    @FXML
    private Button btnLogOut;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtFullName;
    @FXML
    private TextField txtPassword;
    @FXML
    private TextField txtUsername;
    @FXML
    private DatePicker dpLastPasswordChange;

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
        stage.setTitle("EventManagerManagment");

        //Ventana no redimensionable
        stage.setResizable(false);

        // Tabla
        tlUsername.setCellValueFactory(
                new PropertyValueFactory<>("username"));
        tlFullName.setCellValueFactory(
                new PropertyValueFactory<>("fullName"));
        tlEmail.setCellValueFactory(
                new PropertyValueFactory<>("email"));
        tlStatus.setCellValueFactory(
                new PropertyValueFactory<>("status"));
        tlLastPasswordChange.setCellValueFactory(
                new PropertyValueFactory<>("lastPasswordChange"));
        tlManagetCategory.setCellValueFactory(
                new PropertyValueFactory<>("managerCategory"));

        // Table Data
        usersData = FXCollections.observableArrayList(emi.findAll());
        //Set table model.
        tlView.setItems(usersData);

        // ComboBox de UserStatus
        cbStatus.setItems(FXCollections.observableArrayList(UserStatus.values()));

        // ComboBox de ManagerCategorys
        cbManagerCategory.setItems(FXCollections.observableArrayList(Type.values()));

        //
        tlView.getSelectionModel().selectedItemProperty()
                .addListener(this::handleUsersTableSelectionChanged);
        //Accion de cerrar desde la barra de titulo
        //stage.setOnCloseRequest(this::handleCloseRequest);
        //Accion de cerrar desde la barra de titulo
        stage.setOnCloseRequest(this::handleCloseRequest);

        //
        btnCreate.setDisable(false);
        btnModify.setDisable(true);
        btnDelete.setDisable(true);

        //stage.setOnCloseRequest(this::handleCloseRequest);
        stage.show();

    }

    private void handleUsersTableSelectionChanged(ObservableValue observable,
            Object oldValue,
            Object newValue) {
        //If there is a row selected, move row data to corresponding fields in the
        //window and enable create, modify and delete buttons
        if (newValue != null) {
            EventManager eventManager = (EventManager) newValue;
            txtUsername.setText(eventManager.getUsername());
            txtFullName.setText(eventManager.getFullName());
            txtEmail.setText(eventManager.getEmail());
            cbStatus.getSelectionModel().select(eventManager.getStatus());
            cbManagerCategory.getSelectionModel().select(eventManager.getManagerCategory());

            LocalDate date = eventManager.getLastPasswordChange().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            dpLastPasswordChange.setValue(date);

            btnCreate.setDisable(true);
            btnModify.setDisable(false);
            btnDelete.setDisable(false);
        } else {
            //If there is not a row selected, clean window fields 
            txtUsername.setText("");
            txtFullName.setText("");
            txtEmail.setText("");
            cbStatus.getSelectionModel().select("");
            cbManagerCategory.getSelectionModel().select("");
            dpLastPasswordChange.setValue(null);
            dpLastPasswordChange.getEditor().clear();

            btnCreate.setDisable(false);
            btnModify.setDisable(true);
            btnDelete.setDisable(true);
            //ctToggle(rbUsuario);
        }
        //Focus username field
        txtUsername.requestFocus();
    }

    @FXML
    private void handleRemoveEventManger(ActionEvent event) {

        EventManager selectedEventManager=((EventManager)tlView.getSelectionModel()
                                                        .getSelectedItem());
        
        emi.removeEventManager(selectedEventManager);
        
        // Cargamos otra vez la tabla con todos los datos
        usersData = FXCollections.observableArrayList(emi.findAll());
        //Set table model.
        tlView.setItems(usersData);

    }

    private void handleCloseRequest(WindowEvent event) {

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

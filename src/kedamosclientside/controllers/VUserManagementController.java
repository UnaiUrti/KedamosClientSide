package kedamosclientside.controllers;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import kedamosclientside.entities.Category;
import kedamosclientside.entities.EventManager;
import kedamosclientside.entities.User;
import kedamosclientside.entities.UserPrivilege;
import kedamosclientside.entities.UserStatus;
import kedamosclientside.logic.EventManagerFactory;
import kedamosclientside.logic.EventManagerInterface;
import kedamosclientside.logic.UserFactory;
import kedamosclientside.logic.UserInterface;
import kedamosclientside.security.Crypt;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

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
    private TableView<EventManager> tlView;
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
    private ComboBox<UserStatus> cbStatus;
    @FXML
    private ComboBox<Category> cbManagerCategory;
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
    private PasswordField txtPassword;
    @FXML
    private TextField txtUsername;
    @FXML
    private DatePicker dpLastPasswordChange;
    @FXML
    private Label lblUsername;
    @FXML
    private Label lblFullName;
    @FXML
    private Label lblEmail;
    @FXML
    private Label lblPassword;
    @FXML
    private Label lblStatus;
    @FXML
    private Label lblManagerCategory;
    @FXML
    private Label lblLastPasswordChange;

    private ObservableList<EventManager> usersData;

    private UserInterface ui = UserFactory.getUserImplementation();
    private EventManagerInterface emi = EventManagerFactory.getEventManagerImplementation();

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

        // Cargamos la tabla con datos
        loadTableWithData();

        // ComboBox de UserStatus
        cbStatus.setItems(FXCollections.observableArrayList(UserStatus.values()));
        // ComboBox de ManagerCategorys
        cbManagerCategory.setItems(FXCollections.observableArrayList(Category.values()));

        // Añadir un evento al seleccionar una fila de la tabla y mostrar sus datos arrtiba
        tlView.getSelectionModel().selectedItemProperty()
                .addListener(this::handleTableSelectionChanged);

        //Accion de cerrar desde la barra de titulo
        stage.setOnCloseRequest(this::handleCloseRequest);

        // Desabilitar el field del date picker
        dpLastPasswordChange.getEditor().setDisable(true);

        // Habilitar/Desabilitar los botones
        btnCreate.setDisable(false);
        btnModify.setDisable(true);
        btnDelete.setDisable(true);
        btnLogOut.setDisable(false);

        // Hacer invisbles los labels
        lblUsername.setVisible(false);
        lblFullName.setVisible(false);
        lblEmail.setVisible(false);
        lblPassword.setVisible(false);
        lblStatus.setVisible(false);
        lblManagerCategory.setVisible(false);
        lblLastPasswordChange.setVisible(false);

        // Limitar la entrada de caracters 
        txtUsername.textProperty().addListener(this::limitCharacters);
        txtEmail.textProperty().addListener(this::limitCharacters);
        txtFullName.textProperty().addListener(this::limitCharacters);
        txtPassword.textProperty().addListener(this::limitCharacters);

        // Focus en el Username field
        txtUsername.requestFocus();

        stage.show();

    }

    private void handleTableSelectionChanged(ObservableValue observable,
            Object oldValue,
            Object newValue) {

        if (newValue != null) {
            txtPassword.clear();
            EventManager eventManager = (EventManager) newValue;
            txtUsername.setText(eventManager.getUsername());
            txtFullName.setText(eventManager.getFullName());
            txtEmail.setText(eventManager.getEmail());
            cbStatus.getSelectionModel().select(eventManager.getStatus());
            cbManagerCategory.getSelectionModel().select(eventManager.getManagerCategory());
            LocalDate date = eventManager.getLastPasswordChange().toInstant().
                    atZone(ZoneId.systemDefault()).toLocalDate();
            dpLastPasswordChange.setValue(date);

            btnCreate.setDisable(true);
            btnModify.setDisable(false);
            btnDelete.setDisable(false);
        } else {
            //Si no hay ninguna fila seleccionada se limpiara los fields de la ventana 
            clearAllFields();
            btnCreate.setDisable(false);
            btnModify.setDisable(true);
            btnDelete.setDisable(true);
            //ctToggle(rbUsuario);
        }
        //Focus username field
        txtUsername.requestFocus();
    }

    @FXML
    private void handleCreateEventManager(ActionEvent event) {
        clearAllLabels();
        if (informedCreateFields() & emailPattern() & validateCreateUsernameEmail()) {

            // Creamos el event manager con todos sus datos
            EventManager eventManager = new EventManager();
            eventManager.setUsername(txtUsername.getText().trim());
            eventManager.setFullName(txtFullName.getText().trim());
            eventManager.setEmail(txtEmail.getText().trim());
            eventManager.setPassword(Crypt.encryptAsimetric(txtPassword.getText()));
            eventManager.setPrivilege(UserPrivilege.EVENT_MANAGER);

            if (dpLastPasswordChange.getValue() != null) {
                eventManager.setLastPasswordChange(Date.from(dpLastPasswordChange.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            } else {
                eventManager.setLastPasswordChange(new Timestamp(System.currentTimeMillis()));
            }

            eventManager.setStatus((UserStatus) cbStatus.getSelectionModel().getSelectedItem());
            eventManager.setManagerCategory((Category) cbManagerCategory.getSelectionModel().getSelectedItem());

            // Enviamos la peticion al servidor para crear el event manager
            emi.createEventManager(eventManager);

            // Cargamos otra vez la tabla con todos los datos
            loadTableWithData();
            // Limpiamos los labels y los bordes de los fields
            clearAllLabels();
            // Alerta para indicar que se ha creado con exito el event manager
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("The account has been created successfully");
            alert.show();
        }

    }

    @FXML
    private void handleEditEventManger(ActionEvent event) {
        clearAllLabels();
        EventManager em = tlView.getSelectionModel().getSelectedItem();
        if (informedEditFields() & emailPattern() & validateEditUsernameEmail(em)) {

            // Creamos el event manager con todos sus datos
            EventManager eventManager = new EventManager();
            eventManager.setUser_id(em.getUser_id());
            eventManager.setUsername(txtUsername.getText().trim());
            eventManager.setFullName(txtFullName.getText().trim());
            eventManager.setEmail(txtEmail.getText().trim());
            if (!txtPassword.getText().trim().isEmpty()) {
                eventManager.setPassword(Crypt.encryptAsimetric(txtPassword.getText()));
                eventManager.setLastPasswordChange(new Timestamp(System.currentTimeMillis()));
            } else {
                eventManager.setPassword(em.getPassword());
                eventManager.setLastPasswordChange(em.getLastPasswordChange());
            }
            if (dpLastPasswordChange.getValue().equals(em.getLastPasswordChange().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())) {
                eventManager.setLastPasswordChange(em.getLastPasswordChange());
            } else {
                eventManager.setLastPasswordChange(Date.from(dpLastPasswordChange.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            }
            eventManager.setPrivilege(UserPrivilege.EVENT_MANAGER);
            eventManager.setStatus((UserStatus) cbStatus.getSelectionModel().getSelectedItem());
            eventManager.setManagerCategory((Category) cbManagerCategory.getSelectionModel().getSelectedItem());

            // Enviamos la peticion al servidor para crear el event manager
            emi.editEventManager(eventManager);

            // Cargamos otra vez la tabla con todos los datos
            loadTableWithData();
            // Limpiamos los labels y los bordes de los fields
            clearAllLabels();
            // Alerta para indicar que se ha creado con exito el event manager
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("The account has been modified successfully");
            alert.show();
        }

    }

    @FXML
    private void handleRemoveEventManger(ActionEvent event) {

        emi.removeEventManager(tlView.getSelectionModel()
                .getSelectedItem());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("The account has been deleted successfully");
        alert.show();
        loadTableWithData();

    }

    private void clearAllFields() {

        txtUsername.clear();
        txtFullName.clear();
        txtEmail.clear();
        txtPassword.clear();
        cbStatus.getSelectionModel().clearSelection();
        cbManagerCategory.getSelectionModel().clearSelection();
        dpLastPasswordChange.setValue(null);
        dpLastPasswordChange.getEditor().clear();

    }

    private void clearAllLabels() {

        txtEmail.setStyle(null);
        txtFullName.setStyle(null);
        txtPassword.setStyle(null);
        txtUsername.setStyle(null);
        dpLastPasswordChange.setStyle(null);

        lblUsername.setVisible(false);
        lblFullName.setVisible(false);
        lblEmail.setVisible(false);
        lblPassword.setVisible(false);
        lblStatus.setVisible(false);
        lblManagerCategory.setVisible(false);
        lblLastPasswordChange.setVisible(false);

    }

    private boolean validateCreateUsernameEmail() {

        boolean find = true;
        Collection<User> users = ui.findAllUser();

        if (users.stream().filter(u -> u.getUsername().equals(txtUsername.getText().trim())).count() != 0) {
            txtUsername.setStyle("-fx-border-color: red;");
            lblUsername.setText("Username is already in use");
            lblUsername.setVisible(true);
            find = false;
        }
        if (users.stream().filter(u -> u.getEmail().equals(txtEmail.getText().trim())).count() != 0) {
            txtEmail.setStyle("-fx-border-color: red;");
            lblEmail.setText("Email is already in use");
            lblEmail.setVisible(true);
            find = false;
        }
        return find;
    }

    private boolean validateEditUsernameEmail(EventManager em) {

        boolean find = true;
        Collection<User> users = ui.findAllUser();

        if (users.stream().filter(u -> u.getUsername().equals(txtUsername.getText().trim())).count() != 0 & !em.getUsername().
                equalsIgnoreCase(txtUsername.getText().trim())) {
            txtUsername.setStyle("-fx-border-color: red;");
            lblUsername.setText("Username is already in use");
            lblUsername.setVisible(true);
            find = false;
        }
        if (users.stream().filter(u -> u.getEmail().equals(txtEmail.getText().trim())).count() != 0
                & !em.getEmail().equalsIgnoreCase(txtEmail.getText().trim())) {
            txtEmail.setStyle("-fx-border-color: red;");
            lblEmail.setText("Email is already in use");
            lblEmail.setVisible(true);
            find = false;
        }
        return find;

    }

    private boolean informedCreateFields() {
        //logger.info("Iniciado el evento para controlar si el campo esta vacio");

        boolean informed = true;

        if (txtUsername.getText().trim().isEmpty()) {
            lblUsername.setVisible(true);
            lblUsername.setText("Username cannot be empty");
            txtUsername.setStyle("-fx-border-color: red;");
            informed = false;
        }
        if (txtEmail.getText().trim().isEmpty()) {
            lblEmail.setVisible(true);
            lblEmail.setText("Email cannot be empty");
            txtEmail.setStyle("-fx-border-color: red;");
            informed = false;
        }
        if (txtFullName.getText().trim().isEmpty()) {
            lblFullName.setVisible(true);
            lblFullName.setText("Full Name cannot be empty");
            txtFullName.setStyle("-fx-border-color: red;");
            informed = false;
        }
        if (txtPassword.getText().trim().isEmpty()) {
            lblPassword.setVisible(true);
            lblPassword.setText("Password cannot be empty");
            txtPassword.setStyle("-fx-border-color: red;");
            informed = false;
        }
        if (cbStatus.getSelectionModel().getSelectedIndex() == -1) {
            lblStatus.setVisible(true);
            lblStatus.setText("Status cannot be empty");
            cbStatus.setStyle("-fx-border-color: red;");
            informed = false;
        }
        if (cbManagerCategory.getSelectionModel().getSelectedIndex() == -1) {
            lblManagerCategory.setVisible(true);
            lblManagerCategory.setText("Manager Category cannot be empty");
            cbManagerCategory.setStyle("-fx-border-color: red;");
            informed = false;
        }
        return informed;
    }

    private boolean informedEditFields() {
        //logger.info("Iniciado el evento para controlar si el campo esta vacio");

        boolean informed = true;

        if (txtUsername.getText().trim().isEmpty()) {
            lblUsername.setVisible(true);
            lblUsername.setText("Username cannot be empty");
            txtUsername.setStyle("-fx-border-color: red;");
            informed = false;
        }
        if (txtEmail.getText().trim().isEmpty()) {
            lblEmail.setVisible(true);
            lblEmail.setText("Email cannot be empty");
            txtEmail.setStyle("-fx-border-color: red;");
            informed = false;
        }
        if (txtFullName.getText().trim().isEmpty()) {
            lblFullName.setVisible(true);
            lblFullName.setText("Full Name cannot be empty");
            txtFullName.setStyle("-fx-border-color: red;");
            informed = false;
        }
        if (cbStatus.getSelectionModel().getSelectedIndex() == -1) {
            lblStatus.setVisible(true);
            lblStatus.setText("Status cannot be empty");
            cbStatus.setStyle("-fx-border-color: red;");
            informed = false;
        }
        if (cbManagerCategory.getSelectionModel().getSelectedIndex() == -1) {
            lblManagerCategory.setVisible(true);
            lblManagerCategory.setText("Manager Category cannot be empty");
            cbManagerCategory.setStyle("-fx-border-color: red;");
            informed = false;
        }
        return informed;
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
        if (txtPassword.getText().length() > 50) {
            txtPassword.setText(oldValue);
        }

    }

    private boolean emailPattern() {
        //logger.info("Iniciado el evento para controlar si el campo email es valido");
        boolean pattern = true;

        if (!txtEmail.getText().matches("[\\w.]+@[\\w]+\\.[a-zA-Z]{2,4}")) {
            lblEmail.setVisible(true);
            lblEmail.setText("Sorry,only letters (a-z), numbers(0-9), and periods (.) are allowed, plus a required @");
            txtEmail.setStyle("-fx-border-color: red;");
            pattern = false;
        } else {
            lblEmail.setVisible(false);
            txtEmail.setStyle(null);
        }
        return pattern;
    }

    private void handleCloseRequest(WindowEvent event) {

        //Se envia un mensaje al usuario confirmando si de verdad quiere salir
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("EXIT");
        alert.setContentText("are you sure you want to log out?");

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

    private void loadTableWithData() {
        // Obtenemos todos los event managers 
        usersData = FXCollections.observableArrayList(emi.findAll());
        // Hacemos un set de la lista en la tabla
        tlView.setItems(usersData);
    }

    @FXML
    private void handleEventManagerReport(ActionEvent event) {
        try {
            JasperReport report = JasperCompileManager.compileReport("src/kedamosclientside/views/EventManagerReport.jrxml");
            JRBeanCollectionDataSource dataItems = new JRBeanCollectionDataSource((Collection<EventManager>) this.tlView.getItems());
            Map<String, Object> parameters = new HashMap<>();
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataItems);
            JasperViewer jasperViewer = new JasperViewer(jasperPrint);
            jasperViewer.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(VUserManagementController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleLogOut(ActionEvent event) {

        //logger.info("Se ha pulsado el boton back");
        //logger.info("se volvera a la ventana de VSignIn");
        stage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/kedamosclientside/views/VSignIn.fxml"));

        Parent root = null;
        try {
            root = (Parent) loader.load();
        } catch (IOException ex) {
            //logger.severe("Se ha producido un error al cargarel fxml de la ventana singIn");
        }

        VSignInController controller = (VSignInController) loader.getController();
        controller.setStage(stage);
        controller.initStage(root);

    }

}

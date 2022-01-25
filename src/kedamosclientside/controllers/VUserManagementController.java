package kedamosclientside.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import kedamosclientside.entities.EventManager;
import kedamosclientside.logic.EventManagerFactory;
import kedamosclientside.logic.EventManagerInterface;

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
    @FXML
    private TableColumn tlEmail;
    @FXML
    private TableColumn tlFullName;
    @FXML
    private TableColumn tlLastPasswordChange;
    @FXML
    private TableColumn tlPrivilege;
    @FXML
    private TableColumn tlStatus;
    @FXML
    private TableColumn tlUsername;
    @FXML
    private TableColumn tlManagetCategory;

    private ObservableList<EventManager> usersData;

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
        tlEmail.setCellValueFactory(
                new PropertyValueFactory<>("email"));
        tlFullName.setCellValueFactory(
                new PropertyValueFactory<>("fullName"));
        tlLastPasswordChange.setCellValueFactory(
                new PropertyValueFactory<>("lastPasswordChange"));
        tlPrivilege.setCellValueFactory(
                new PropertyValueFactory<>("privilege"));
        tlStatus.setCellValueFactory(
                new PropertyValueFactory<>("status"));
        tlUsername.setCellValueFactory(
                new PropertyValueFactory<>("username"));
        tlManagetCategory.setCellValueFactory(
                new PropertyValueFactory<>("managerCategory"));

        // Table Data
        usersData = FXCollections.observableArrayList(emi.findAll());
        //Set table model.
        tlView.setItems(usersData);

        //Accion de cerrar desde la barra de titulo
        //stage.setOnCloseRequest(this::handleCloseRequest);
        //Accion de cerrar desde la barra de titulo
        //stage.setOnCloseRequest(this::handleCloseRequest);
        stage.show();

    }

}

package kedamosclientside.controllers;

import kedamosclientside.exceptions.EmptyFieldsException;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SingleSelectionModel;
import java.time.LocalDate;
import java.util.EmptyStackException;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import kedamosclientside.entities.Category;
import kedamosclientside.entities.Event;
import kedamosclientside.entities.PersonalResource;
import kedamosclientside.entities.Place;
import kedamosclientside.exceptions.ClientLogicException;
import kedamosclientside.exceptions.MaxCharacterException;
import kedamosclientside.logic.EventInterface;

/**
 * FXML Controller class
 *
 * @author Adrian Franco
 */
public class MyEventsViewController {

    private final static Logger LOGGER = Logger.getLogger("client.controllers.MyEventsViewController");
    private Stage stage;
    private EventInterface eventinterface;

    public void setEventinterface(EventInterface eventinterface) {
        this.eventinterface = eventinterface;
    }

    @FXML
    private TableView<Event> tvTable;
    @FXML
    private TableColumn<Event, String> tcTable;
    @FXML
    private TableColumn<Event, String> tcDescription;
    @FXML
    private TableColumn<Event, Float> tcPrice;
    @FXML
    private TableColumn<Event, Category> tcCategory;
    @FXML
    private TableColumn<Event, Date> tcDate;
    @FXML
    private TableColumn<Event, Long> tcMinParticipants;
    @FXML
    private TableColumn<Event, Long> tcMaxParticipants;
    @FXML
    private TableColumn<Event, Long> tcActualParticipants;
    @FXML
    private TableColumn<Event, Place> tcPlace;
    @FXML
    private TableColumn<Event, PersonalResource> tcPersonal;
    @FXML
    private ComboBox<Category> cmbCategory;
    @FXML
    private TextField tfTitle;
    @FXML
    private DatePicker dpDate;
    @FXML
    private Button btnCreate;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnModify;
    @FXML
    private TextField tfMaxParticipants;
    @FXML
    private TextField tfMinParticipants;
    @FXML
    private Button btnAddPlace;
    @FXML
    private TextField tfPrice;
    @FXML
    private Button btnAddPersonal;
    @FXML
    private TextArea taDescription;
    @FXML
    private CheckBox cbAccepted;
    @FXML
    private Button btnPrint;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Initializes the controller class.
     *
     * @param root
     */
    //Acordarme q urti e irkus me devuelven el objeto event con place y/o personal
    //resoruce añadido
    public void initStage(Parent root) {

        Scene scene = new Scene(root);
        stage = new Stage();
        //Set stage properties
        //stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setTitle("Event");
        stage.setResizable(false);

        tcTable.setCellValueFactory(new PropertyValueFactory<>("title"));
        tcDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
        tcPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
        tcCategory.setCellValueFactory(new PropertyValueFactory<>("Category"));
        tcDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
        tcMinParticipants.setCellValueFactory(new PropertyValueFactory<>("MinParticipants"));
        tcMaxParticipants.setCellValueFactory(new PropertyValueFactory<>("MaxParticipants"));
        tcActualParticipants.setCellValueFactory(new PropertyValueFactory<>("ActualParticipants"));
        tcPlace.setCellValueFactory(new PropertyValueFactory<>("Place"));
        tcPersonal.setCellValueFactory(new PropertyValueFactory<>("Personal"));

        tvTable.getSelectionModel().selectedItemProperty()
                .addListener(this::handleTableSelectionChanged);
        stage.setOnCloseRequest(this::handleCloseRequest);

        //Add the combobox values
        ObservableList<Category> comboItems;
        comboItems = FXCollections.observableArrayList(
                Category.CULTURA,
                Category.DEPORTES,
                Category.EXCURSIONES,
                Category.FIESTA,
                Category.JUEGOS_DE_MESA,
                Category.MUSICA,
                Category.OCIO,
                Category.VIDEOJUEGOS,
                Category.OTROS
        );
        cmbCategory.setItems(comboItems);
        cmbCategory.getSelectionModel().selectFirst();
        try {
            Collection<Event> events = eventinterface.getEvents();
            ObservableList<Event> eventsForTable = FXCollections.observableArrayList(events);
            tvTable.setItems(eventsForTable);
        } catch (ClientLogicException ex) {
            Logger.getLogger(MyEventsViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        btnCreate.setDisable(false);
        btnAddPersonal.setDisable(true);
        btnAddPlace.setDisable(true);
        btnModify.setDisable(true);
        btnDelete.setDisable(true);

        tfTitle.requestFocus();
        stage.show();
    }

    /**
     * Metodo tipo booleano que comprueba si los campos estan informados o no
     *
     * @return Devuelve una variable booleana que dice hay algun campo vacio (si
     * es false hay un campo vacio, si es true esta todo informado)
     */
    private boolean informedFields() throws EmptyFieldsException, MaxCharacterException {

        LOGGER.info("Se esta comprobando si algun campo esta vacio");

        if (tfTitle.getText().trim().isEmpty()
                || taDescription.getText().isEmpty()
                || dpDate.getValue() == null
                || tfPrice.getText().trim().isEmpty()
                || tfMinParticipants.getText().isEmpty()
                || tfMaxParticipants.getText().isEmpty()) {
            LOGGER.warning("Algun campo esta vacio");
            throw new EmptyFieldsException("Algunos campos estan vacios");

        }
        if (tfTitle.getText().trim().length() > 255
                || taDescription.getText().trim().length() > 255
                || tfPrice.getText().trim().length() > 255
                || tfMinParticipants.getText().trim().length() > 255
                || tfMaxParticipants.getText().trim().length() > 255) {
            LOGGER.warning("Algun campo tiene mas de 255 caracteres");
            throw new MaxCharacterException("Algun campo tiene mas de 255 caracteres");

        }
        if (dpDate.getValue() == null) {
            LOGGER.info("El campo Date esta vacio y se va a enviar un "
                    + "mensaje al usuario");

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Introduce La fecha del evento");
            alert.setContentText("No puedes dejar el campo vacio");
            alert.show();
            return false;
        }
        if (cmbCategory.getValue() == null) {
            LOGGER.info("El campo Category esta vacio y se va a enviar un "
                    + "mensaje al usuario");

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Introduce una Categoria al Evento");
            alert.setContentText("No puedes dejar el campo vacio");
            alert.show();
            return false;

        }
        {
            LOGGER.info("Todos los campos estan informados");

            return true;
        }

    }

    @FXML
    private void handleComboCategory(ActionEvent event) {
    }

    @FXML
    private void handleCreate(ActionEvent event) throws ClientLogicException {

        Event newEvent = new Event();

        newEvent.setTitle(tfTitle.getText().trim());
        newEvent.setDescription(taDescription.getText().trim());
        newEvent.setDate(Date.from(dpDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        //newEvent.setCategory(cmbCategory.getValue());
        newEvent.setPrice(Float.valueOf(0));
        newEvent.setMinParticipants(Long.valueOf(0));
        newEvent.setMaxParticipants(Long.valueOf(0));

        newEvent.setEvent_id(null);

        eventinterface.createEvent(newEvent);

        tfTitle.setText("");
        taDescription.setText("");
        dpDate.setValue(null);
        cmbCategory.setValue(null);
        tfPrice.setText("");
        tfMinParticipants.setText("");
        tfMaxParticipants.setText("");
        //Aqui hacer update

        modifyTable();

    }

    @FXML
    private void handleDelete(ActionEvent event) {
        LOGGER.info("Boton para borrar Evento de la BD");

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Delete Event");
        alert.setContentText("Seguro que quieres eliminar el Evento?");
        Optional<ButtonType> resp = alert.showAndWait();
        if (resp.get() == ButtonType.OK) {
            try {
                LOGGER.info("Borrar evento");

                Event deleteEvent = tvTable.getSelectionModel().getSelectedItem();
                eventinterface.removeEvent(deleteEvent);

                tvTable.refresh();
            } catch (Exception ex) {
                Logger.getLogger(MyEventsViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Method to control if there's a item selected in the table
     *
     * @param observableValue
     * @param oldValue
     * @param newValue
     */
    private void handleTableSelectionChanged(ObservableValue observableValue, Object oldValue, Object newValue) {

        if (newValue != null) {
            //CUANDO CLICKA
            Event event = tvTable.getSelectionModel().getSelectedItem();
            tfTitle.setText(event.getTitle());
            taDescription.setText(event.getDescription());
            Date date = event.getDate();
            LocalDate localdateConverted = date.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            dpDate.setValue(localdateConverted);
            cmbCategory.getSelectionModel().select(Category.valueOf(event.getCategory()));
            tfPrice.setText(Float.toString(event.getPrice()));
            tfMinParticipants.setText(Long.toString(event.getMinParticipants()));
            tfMaxParticipants.setText(Long.toString(event.getMaxParticipants()));
            btnModify.setDisable(false);
            btnAddPersonal.setDisable(false);
            btnAddPlace.setDisable(false);

        } else {
            //CUANDO DES-SELECCIONA
            tfTitle.setText("");
            taDescription.setText("");
            dpDate.setValue(null);
            cmbCategory.setValue(null);
            tfPrice.setText("");
            tfMinParticipants.setText("");
            tfMaxParticipants.setText("");

            //Deshabilitar los botones 
            btnCreate.setDisable(true);
            btnAddPersonal.setDisable(true);
            btnAddPlace.setDisable(true);
            btnModify.setDisable(true);
        }
    }

    @FXML
    private void handleBack(ActionEvent event) {
    }

    @FXML
    private void handleModify(ActionEvent event) {

        try {
            if (informedFields()) {
                Event newEvent = new Event();

                newEvent.setTitle(tfTitle.getText().trim());
                newEvent.setDescription(taDescription.getText().trim());
                newEvent.setDate(Date.from(dpDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                newEvent.setCategory(String.valueOf(cmbCategory.getValue()));
                newEvent.setPrice(Float.valueOf(tfPrice.getText().trim()));
                newEvent.setMinParticipants(Long.valueOf(tfMinParticipants.getText().trim()));
                newEvent.setMaxParticipants(Long.valueOf(tfMaxParticipants.getText().trim()));
                newEvent.setEvent_id(tvTable.getSelectionModel().getSelectedItem().getEvent_id());

                eventinterface.editEvent(newEvent);
                //Aqui hacer update
                modifyTable();
                tfTitle.setText("");
                taDescription.setText("");
                dpDate.setValue(null);
                cmbCategory.setValue(null);
                tfPrice.setText("");
                tfMinParticipants.setText("");
                tfMaxParticipants.setText("");
           }

       } catch (EmptyFieldsException ex) {
            //alerta
            // en el set context header ponerle ex.getMessage()
      } catch (MaxCharacterException ex) {
            //alerta
        } catch (ClientLogicException ex) {
            Logger.getLogger(MyEventsViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleAddPlace(ActionEvent event) {
    }

    @FXML
    private void handleAddPersonal(ActionEvent event) {
    }

    @FXML
    private void handlePrint(ActionEvent event) {
    }

    @FXML
    private void handleCloseRequest(WindowEvent event) {

        LOGGER.info("Se ha pulsado la opción de cerrar la ventana");

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("EXIT");

        alert.setContentText("Quieres cerrar la aplicacion?");

        Optional<ButtonType> resp = alert.showAndWait();

        if (resp.get() == ButtonType.OK) {
            LOGGER.info("Se ha pulsado Confirm, el programa se va a cerrar");
            stage.close();
        } else {
            LOGGER.info("Se ha cancelado la request, continuua en la misma ventana");
            event.consume();
        }
    }

    private void modifyTable() {
        tvTable.getItems().clear();
        try {
            Collection<Event> events = eventinterface.getEvents();
            ObservableList<Event> eventsInTable = FXCollections.observableArrayList(events);
            tvTable.setItems(eventsInTable);

        } catch (Exception e) {

        }
    }

}

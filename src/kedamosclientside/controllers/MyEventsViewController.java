package kedamosclientside.controllers;

import java.io.IOException;
import java.net.ConnectException;
import java.text.SimpleDateFormat;
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
import java.util.Calendar;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import kedamosclientside.entities.Category;
import kedamosclientside.entities.Event;
import kedamosclientside.entities.PersonalResource;
import kedamosclientside.entities.Place;
import kedamosclientside.exceptions.EventDateBadException;
import kedamosclientside.exceptions.EventParticipantsException;
import kedamosclientside.exceptions.EventPriceException;
import kedamosclientside.exceptions.MaxCharacterException;
import kedamosclientside.logic.EventFactory;
import kedamosclientside.logic.EventInterface;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 * FXML Controller class
 *
 * @author Adrian Franco
 */
public class MyEventsViewController {

    private final static Logger LOGGER = Logger.getLogger("kedamosclientside.controllers.MyEventsViewController");
    private Stage stage;
    private EventInterface eventinterface = EventFactory.getEvent();

    /**
     * Set de la interfaz evento
     *
     * @param eventinterface
     */
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

    private Event e;

    /**
     * Return de la ventana
     *
     * @return
     */
    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Initializes the controller class.
     *
     * @param root nodo del grafo de la escena
     * @param event Parametro que puede llegar con datos de personal o place
     *
     * Este metodo pretende inicializar la escena y el escenario.
     */
    //Acordarme q urti e irkus me devuelven el objeto event con place y/o personal
    public void initStage(Parent root) {

        Scene scene = new Scene(root);

        //Set stage properties
        //stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setTitle("Event");
        //No redimensionable
        stage.setResizable(false);

        //Datos a mostrar para la Tabla
        tcTable.setCellValueFactory(new PropertyValueFactory<>("title"));
        tcDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
        tcPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
        tcCategory.setCellValueFactory(new PropertyValueFactory<>("Category"));
        tcDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
        tcDate.setCellFactory(column -> {
            TableCell<Event, Date> cell = new TableCell<Event, Date>() {
                private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        this.setText(format.format(item));

                    }
                }
            };

            return cell;
        });
        tcMinParticipants.setCellValueFactory(new PropertyValueFactory<>("MinParticipants"));
        tcMaxParticipants.setCellValueFactory(new PropertyValueFactory<>("MaxParticipants"));
        tcActualParticipants.setCellValueFactory(new PropertyValueFactory<>("ActualParticipants"));

        tvTable.getSelectionModel().selectedItemProperty()
                .addListener(this::handleTableSelectionChanged);
        stage.setOnCloseRequest(this::handleCloseRequest);

        //Añadimos los valores de la combo, y seleccionamos el primero por defecto
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
        
        tableLoad();
        //Se enfoca en el campo Title
        tfTitle.requestFocus();
        //Botones create, modify, delere, add place y add personal, deshabilitados
        btnCreate.setDisable(false);
        btnAddPersonal.setDisable(true);
        btnAddPlace.setDisable(true);
        btnModify.setDisable(true);
        btnDelete.setDisable(true);

        //Print y Back habilitados
        btnPrint.setDisable(false);
        btnBack.setDisable(false);

        stage.show();
    }

    /**
     * Metodo tipo booleano que comprueba si los campos estan informados o no o
     * superan el limite de caracteres
     *
     * @return Devuelve una variable booleana que dice hay algun campo vacio (si
     * es false hay un campo vacio, si es true esta todo informado)
     */
    private boolean informedFields() throws EmptyFieldsException, MaxCharacterException {

        LOGGER.info("Se esta comprobando si algun campo esta vacio o supera el limite de valores permitido");

        if (tfTitle.getText().trim().isEmpty()) {
            LOGGER.warning("El campo Title esta vacio");
            throw new EmptyFieldsException("Introduce un Titulo al evento");
        }
        if (taDescription.getText().trim().isEmpty()) {
            LOGGER.warning("El campo description esta vacio");
            throw new EmptyFieldsException("Introduce una breve descripcion");
        }
        if (dpDate.getValue() == null) {
            LOGGER.warning("El campo Date esta vacio");
            throw new EmptyFieldsException("Introduce la fecha del Evento");
        }
        if (tfMinParticipants.getText().trim().isEmpty()) {
            LOGGER.warning("El campo minParticipants está vacio");
            throw new EmptyFieldsException("Introduce el minimo de participantes para el evento");
        }
        if (tfMaxParticipants.getText().trim().isEmpty()) {
            LOGGER.warning("El campo maxParticipants está vacio");
            throw new EmptyFieldsException("Introduce el maximo de participantes para el evento");
        }
        if (tfPrice.getText().trim().isEmpty()) {
            LOGGER.warning("El campo Price está vacio");
            throw new EmptyFieldsException("Introduce el precio para el evento");
        }
        if (tfTitle.getText().trim().length() > 255) {
            LOGGER.warning("Algun campo tiene mas de 255 caracteres");
            throw new MaxCharacterException("Algun campo tiene mas de 255 caracteres");
        }
        if (taDescription.getText().trim().length() > 255) {
            LOGGER.warning("el campo description tiene mas de 255 caracteres");
            throw new MaxCharacterException("El campo Description tiene mas de 255 caracteres");
        }
        if (tfPrice.getText().trim().length() > 255) {
            LOGGER.warning("El campo Price tiene mas de 255 caracteres");
            throw new MaxCharacterException("El campoprice tiene mas de 255 caracteres");
        }
        if (tfMinParticipants.getText().trim().length() > 255) {
            LOGGER.warning("El campo minParticipants tiene mas de 255 caracteres");
            throw new MaxCharacterException("El campo minParticipants tiene mas de 255 caracteres");
        }
        if (tfMaxParticipants.getText().trim().length() > 255) {
            LOGGER.warning("El campo maxParticipants tiene mas de 255 caracteres");
            throw new MaxCharacterException("El campo MaxParticipants tiene mas de 255 caracteres");
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

    /**
     * Metodo para crear eventos con las excepciones correspondientes
     *
     * @param event parametro que puede recibir con datos de place o personal
     */
    @FXML
    private void handleCreate(ActionEvent event) {

        try {
            Event newEvent = new Event();

            informedFields();
            fieldsValidations();

            newEvent.setTitle(tfTitle.getText().trim());
            newEvent.setDescription(taDescription.getText().trim());
            newEvent.setDate(Date.from(dpDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            newEvent.setCategory(String.valueOf(cmbCategory.getValue()));
            newEvent.setPrice(Float.valueOf(tfPrice.getText().trim()));
            newEvent.setMinParticipants(Long.valueOf(tfMinParticipants.getText().trim()));
            newEvent.setMaxParticipants(Long.valueOf(tfMaxParticipants.getText().trim()));

            //newEvent.setEvent_id(0L);
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

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("An event has been created successfully");
            alert.showAndWait();
        } catch (ConnectException ex) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Server Error");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        } catch (EmptyFieldsException ex) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Fields Empty");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        } catch (MaxCharacterException ex) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Too much characters");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        } catch (EventDateBadException ex) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Fecha incorrecta");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        } catch (EventParticipantsException ex) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Participants incorrecto");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        } catch (EventPriceException ex) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Price incorrecto");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }

    }

    /**
     * Metodo para borrar eventos de la BD
     *
     * @param event parametro que puede recibir con datos de place o personal
     */
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
                tfTitle.setText("");
                taDescription.setText("");
                dpDate.setValue(null);
                cmbCategory.setValue(null);
                tfPrice.setText("");
                tfMinParticipants.setText("");
                tfMaxParticipants.setText("");

                //Actualizar la tabla
                modifyTable();
            } catch (ConnectException ex) {
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setTitle("CONNECTION ERROR");
                alert2.setContentText("Hubo un error al conectarse con el servidor. Prueba mas tarde");
                alert2.showAndWait();
            }
        }
    }

    /**
     * Metodo para controlar cuando clicka una fila
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
            btnDelete.setDisable(false);
            btnCreate.setDisable(true);
            this.e = event;
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
            btnCreate.setDisable(false);
            btnAddPersonal.setDisable(true);
            btnAddPlace.setDisable(true);
            btnModify.setDisable(true);
            btnDelete.setDisable(true);
        }
    }

    /**
     * Metodo para volver a la ventana anterior
     *
     * @param event recibido con datos de place o personal
     */
    @FXML
    private void handleBack(ActionEvent event) throws IOException {

        stage.close();
        FXMLLoader loader;
        loader = new FXMLLoader(getClass().getResource("/kedamosclientside/views/VMainMenu.fxml"));

        Parent roota = (Parent) loader.load();

        VMainMenuController controller = ((VMainMenuController) loader.getController());
        // controller.setEventinterface(EventFactory.getEvent());
        controller.setStage(new Stage());
        controller.initStage(roota);

    }

    /**
     * Metodo para modificar eventos en la BD
     *
     * @param event recibido con datos de place o personal
     */
    @FXML
    private void handleModify(ActionEvent event) {

        try {
            if (informedFields()) {
                fieldsValidations();
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
                cmbCategory.getSelectionModel().selectFirst();
                tfPrice.setText("");
                tfMinParticipants.setText("");
                tfMaxParticipants.setText("");

                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("An event has been modify successfully");
                alert.showAndWait();
            }

        } catch (ConnectException ex) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Server Error");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        } catch (EmptyFieldsException ex) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Fields Empty");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        } catch (MaxCharacterException ex) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Too much characters");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        } catch (EventDateBadException ex) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Fecha incorrecta");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        } catch (EventParticipantsException ex) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Participants incorrecto");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        } catch (EventPriceException ex) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Price incorrecto");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }

    /**
     * Mettodo para abrir la ventana Place
     *
     * @param event le enviamos event con datos
     */
    @FXML
    private void handleAddPlace(ActionEvent event) throws IOException {
        stage.close();
        FXMLLoader loader;
        loader = new FXMLLoader(getClass().getResource("/kedamosclientside/views/VPlace.fxml"));

        Parent root = (Parent) loader.load();

        VPlaceController controller = ((VPlaceController) loader.getController());
        // controller.setEventinterface(EventFactory.getEvent());
        controller.setStage(new Stage());
        controller.initStage(root, e);

    }

    /**
     * Metodo para abrir la ventana Personal
     *
     * @param event enviamos event con datos
     */
    @FXML
    private void handleAddPersonal(ActionEvent event) throws IOException {

        stage.close();
        FXMLLoader loader;
        loader = new FXMLLoader(getClass().getResource("/kedamosclientside/views/VPersonalResource.fxml"));

        Parent root = (Parent) loader.load();

        VPersonalResourceController controller = ((VPersonalResourceController) loader.getController());
        //controller.setEventinterface(EventFactory.getEvent());
        controller.setStage(new Stage());
        //controller.setEvent
        controller.initStage(root, e);

    }

    /**
     * Metrodo para imprimir el informe
     *
     * @param event
     */
    @FXML
    private void handlePrint(ActionEvent event) {

        LOGGER.info("Opcion para imprimir el informe");
        try {
            LOGGER.info("Imprimiendo informe...");
            JasperReport report = JasperCompileManager.compileReport(getClass().getResourceAsStream("/kedamosclientside/report/EventReport.jrxml"));
            JRBeanCollectionDataSource dataItems = new JRBeanCollectionDataSource((Collection<Event>) this.tvTable.getItems());
            Map<String, Object> parameters = new HashMap<>();
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataItems);
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.setVisible(true);

        } catch (JRException ex) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("No se ha imprimido informacion");
            alert.setHeaderText("Error Report");
            alert.setContentText("Ha habido un error al imprimir los datos");
            alert.showAndWait();
        }

    }

    /**
     * Metodo para salir de la aplicacion si pulsa el usuario
     *
     * @param event
     */
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

    /**
     * Metodo para actualizar la tabla
     */
    private void modifyTable() {
        tvTable.getItems().clear();
        try {
            Collection<Event> events = eventinterface.getEvents();
            ObservableList<Event> eventsInTable = FXCollections.observableArrayList(events);
            tvTable.setItems(eventsInTable);

        } catch (Exception e) {
            LOGGER.info("No se ha podido modificar la tabla");
        }
    }

    /**
     * Metodo para hacer todas las validaciones de los campos
     *
     * @throws EventDateBadException excepcion para problemas con la fecha
     * @throws EventParticipantsException excepcion para problemas con min y max
     * participants
     * @throws EventPriceException excepcion para problemas con el precio
     */
    private void tableLoad() {
        try {
            Collection<Event> events = eventinterface.getEvents();
            ObservableList<Event> eventsForTable = FXCollections.observableArrayList(events);
            tvTable.setItems(eventsForTable);
        } catch (ConnectException ex) {
            LOGGER.severe("Error de conexion con el servidor");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Server error wait a few minutes");
            alert.showAndWait();
        }
    }

    private void fieldsValidations() throws EventDateBadException, EventParticipantsException, EventPriceException {

        if (dpDate.getValue() != null) {
            if (Date.from(dpDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()).before(Calendar.getInstance().getTime())) {
                LOGGER.warning("La fecha introducida es menor a la del dia de hoy");
                throw new EventDateBadException("La fecha es inferior o igual a la de hoy");
            }
        }
        if (!tfMinParticipants.getText().matches("[0-9]{1,4}")) {

            LOGGER.warning("Los Participantes no admiten letras");
            throw new EventParticipantsException("Los campos participants solo admiten numeros positivos");
        }
        if (!tfMaxParticipants.getText().matches("[0-9]{1,4}")) {

            LOGGER.warning("Los Participantes no admiten letras");
            throw new EventParticipantsException("Los campos participants solo admiten numeros positivos");
        }
        if (Integer.parseInt(tfMinParticipants.getText()) > Integer.parseInt(tfMaxParticipants.getText())) {

            LOGGER.warning("El minimo de participantes no puede superar al maximo");
            throw new EventParticipantsException("MinParticipants es mayor que Maxparticipants");

        }

        if (!tfMaxParticipants.getText().matches("[0-9]{1,4}")) {
            LOGGER.warning("Los Participantes no admiten letras");
            throw new EventParticipantsException("Los campos participants solo admiten numeros positivos");

        }
        if (!tfPrice.getText().matches("[0-9]{1,5}[.][0-9]{1,2}") && !tfPrice.getText().matches("[0-9]{1,5}") && !tfPrice.getText().matches("")) {
            throw new EventPriceException("El campo Price solo puede contener numeros");
        }
    }

}

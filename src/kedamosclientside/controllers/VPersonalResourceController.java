package kedamosclientside.controllers;

import java.io.IOException;
import java.net.ConnectException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collection;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import kedamosclientside.entities.Event;
import kedamosclientside.entities.EventManager;
import kedamosclientside.entities.PersonalResource;
import kedamosclientside.entities.Type;
import kedamosclientside.exceptions.DateHiredIsAfterException;
import kedamosclientside.exceptions.IncorrectPriceException;
import kedamosclientside.exceptions.IncorrectQuantityException;
import kedamosclientside.exceptions.PersonalDuplicatedException;
import kedamosclientside.logic.PersonalResourceImplementation;
import kedamosclientside.logic.PersonalResourceInterface;

import kedamosclientside.logic.PersonalResourceManager;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

/**
 * Clase controladora de la ventana Personal resource.
 *
 * @author Irkus De La Fuente
 */
public class VPersonalResourceController {

    private final static Logger logger = Logger.getLogger("client.controllers.VSignUpController");
    private Stage stage;
    private Tooltip tooltip;

    @FXML
    private Button printBtn, modifyBtn, backBtn, deleteBtn, createbtn;
    @FXML
    private DatePicker hiredDate, expiredDate;
    @FXML
    private TableView table;
    @FXML
    private ComboBox typeCombo;
    @FXML
    private TextField priceText, quantityText;
    @FXML
    private TableColumn typecolum, quantitycolum, pricecolum, expiredcolum, hiredcolum;
    private Long pid;

    private PersonalResource p;
    private Event ev;
    private ObservableList<PersonalResource> data;
    private PersonalResourceInterface personalIface = PersonalResourceManager.getImplementation();

    /**
     *
     * @return
     */
    public Stage getStage() {
        return stage;
    }

    /**
     *
     * @param stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Este metodo pretende inicializar el la escena y el escenario.
     *
     * @param root nodo del grafo de la escena
     * @param id
     */
    public void initStage(Parent root, Event id) {
        try {
            logger.info("Iniciado initStage de la ventana PersonalResource");
            Scene scene = new Scene(root);
            this.ev = id;
            stage.setTitle("PersonalResource");
            logger.info("Caracteristicas del init resizeable false botones deshabilitados etc");
            stage.setResizable(false);
            modifyBtn.setDisable(true);
            deleteBtn.setDisable(true);
            createbtn.setDisable(true);
            logger.info("Asignar tipos a las columnas de las tablas");
            typecolum.setCellValueFactory(
                    new PropertyValueFactory<>("type"));
            quantitycolum.setCellValueFactory(
                    new PropertyValueFactory<>("quantity"));
            pricecolum.setCellValueFactory(
                    new PropertyValueFactory<>("price"));
            expiredcolum.setCellValueFactory(
                    new PropertyValueFactory<>("dateExpired"));
            hiredcolum.setCellValueFactory(
                    new PropertyValueFactory<>("dateHired"));
            logger.info("Llamada al metodo de cargar la tabla");
            tableLoad(id.getEvent_id());

            logger.info("Alineamos campos numericos a la derecha");
            quantitycolum.setStyle("-fx-alignment: CENTER-RIGHT;");
            pricecolum.setStyle("-fx-alignment: CENTER-RIGHT;");
            logger.info("Asignamos valores a la combo");
            typeCombo.getItems().addAll(Type.values());
            //Metodo de selecion de la fila
            table.getSelectionModel().selectedItemProperty()
                    .addListener((observable, oldValue, newValue) -> {
                        logger.info("metodo de seleccion de fila");
                        if (newValue != null) {
                            //Si seleciona ponemos los valores de la fila arriba y habilitamos botones
                            PersonalResource per = (PersonalResource) newValue;
                            this.p = per;
                            this.pid = per.getPersonalresource_id();
                            LocalDate datehired = per.getDateHired().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                            LocalDate dateexpired = per.getDateExpired().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                            hiredDate.setValue(datehired);

                            expiredDate.setValue(dateexpired);

                            priceText.setText(per.getPrice().toString());
                            quantityText.setText(per.getQuantity().toString());
                            typeCombo.getSelectionModel().select(per.getType());
                            deleteBtn.setDisable(false);
                            modifyBtn.setDisable(false);

                        } else {
                            //Si deselecciona desabilitamos botones y limpiamos campos
                            clearFields();
                            deleteBtn.setDisable(true);
                            modifyBtn.setDisable(true);
                        }
                    });

            backBtn.setOnAction(action -> {
                try {
                    logger.info("metodo boton back volver a event");
                    
                    //Abrir la ventana de eventos
                    stage.close();
                    FXMLLoader loader;
                    loader = new FXMLLoader(getClass().getResource("/kedamosclientside/views/MyEventsView.fxml"));
                    
                    
                    Parent roota = (Parent) loader.load();
                    
                    MyEventsViewController controller = ((MyEventsViewController) loader.getController());
                    // controller.setEventinterface(EventFactory.getEvent());
                    controller.setStage(new Stage());
                    controller.initStage(roota);
                } catch (IOException ex) {
                  Alert alert=new Alert(Alert.AlertType.ERROR);
                  alert.setContentText("Error al volver a la ventana de eventos");
                  alert.showAndWait();
                }
              

            });
            logger.info("Asignar acciones");
            stage.setOnCloseRequest(this::handleCloseRequest);
            deleteBtn.setOnAction(this::handleDeleteRequest);
            quantityText.textProperty().addListener(this::handleNumericType);
            priceText.textProperty().addListener(this::handleDecimalType);
            modifyBtn.setOnAction(this::handleModifyRequest);
            createbtn.setOnAction(this::handleCreateRequest);
            printBtn.setOnAction(this::handlePrintRequest);
            //Comprobacion de que los campos esten rellenos para habilitar botones
            informedFields();

            stage.setScene(scene);
            logger.info("Mostramos la ventana");
            stage.show();
        } catch (ConnectException ex) {
            logger.severe("Error al cargar la tabla");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("The window cant be open please check the server");
        }
    }

    /**
     * Este metodo abre un reporte de los datos de la tabla
     *
     * @param action
     */
    @FXML
    private void handlePrintRequest(ActionEvent action) {
        logger.info("Metodo report");
        try {
            JasperReport report = JasperCompileManager.compileReport("src/kedamosclientside/report/PersonalResourceReport.jrxml");
            JRBeanCollectionDataSource dataItems = new JRBeanCollectionDataSource((Collection<PersonalResource>) this.table.getItems());
            Map<String, Object> parameters = new HashMap<>();
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataItems);
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.setVisible(true);
        } catch (JRException ex) {
            logger.severe("Error al abrir el report");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Error during loading the report");
            alert.showAndWait();
        }
    }

    /**
     * Este metodo pretende controlar la salida de la ventana cuando se pulse la
     * X de la barra de titulo.
     *
     * @param event representa la accion del evento handleCloseRequest
     */
    private void handleCloseRequest(WindowEvent event) {
        logger.info("Se ha pulsado la X de la barra de titulo y se enviara un "
                + "aviso de confirmacion");

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("EXIT");

        alert.setContentText("Do you really want to exit?");

        Optional<ButtonType> answer = alert.showAndWait();
        if (answer.get() == ButtonType.OK) {
            logger.info("Se ha pulsado OK y el programa va a finalizar");
            stage.close();
        } else {
            logger.info("Se ha cancelado el closeRequest");
            event.consume();
        }

    }

    /**
     * Este metodo carga los datos de la tabla
     *
     * @param id
     * @throws ConnectException
     */
    public void tableLoad(Long id) throws ConnectException {

        logger.info("Iniciado metodo cargar tabla");
        data = FXCollections.observableArrayList(personalIface.getPersonalByEvent(id));

        table.setItems(data);

    }

    private void handleDeleteRequest(ActionEvent action) {
        logger.info("iniciando el metodo de borrar");
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Do you really want to delete this personal?");
            Optional<ButtonType> answer = alert.showAndWait();
            if (answer.get() == ButtonType.OK) {
                logger.info("Se ha pulsado OK y el programa el objeto se borrara");
                //le pasamos la id a la implementacion
                personalIface.deletePersonal(this.pid);
                //recargamos la tabla
                tableLoad(ev.getEvent_id());
                //limpiamos los campos
                clearFields();
                //desabilitamos botones
                deleteBtn.setDisable(true);
                modifyBtn.setDisable(true);

            } else {
                //cancelar el delete
                logger.info("Se ha cancelado el delete request");
                action.consume();
            }
        } catch (ConnectException e) {
            logger.severe("Error de conexion con el servidor");

            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setContentText("Server error wait a few minutes");
            alerta.showAndWait();
        }
    }

    /**
     * Este metodo controla que solo pueda meter caracteres numericos
     *
     * @param observable
     * @param oldValue
     * @param newValue
     */
    private void handleNumericType(ObservableValue observable,
            String oldValue,
            String newValue) {
        //si no es numerico no le dejamos escribir
        if (!quantityText.getText().trim().matches("\\d*")) {
            quantityText.setText(oldValue);
        }
    }

    /**
     * Este metodo comprueba que solo pueda meter caracteres numericos y un
     * punto
     *
     * @param observable
     * @param oldValue
     * @param newValue
     */
    private void handleDecimalType(ObservableValue observable,
            String oldValue,
            String newValue) {
        //si no es un numero o un punto no escribe no puede escribir mas de un punto
        if (!priceText.getText().trim().matches("\\d*(\\.\\d*)?")) {
            priceText.setText(oldValue);
        }
    }

    /**
     * Este metodo controla la accion de modificar
     *
     * @param action
     */
    private void handleModifyRequest(ActionEvent action) {
        logger.info("Metodo de modificar");
        try {
            if (modifyComprobation()) {

                logger.info("Validaciones pasadas");
                PersonalResource selectedPersonal = ((PersonalResource) table.getSelectionModel()
                        .getSelectedItem());
                //asignamos los nuevos valores al personal
                selectedPersonal.setDateExpired(Date.from(expiredDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                selectedPersonal.setDateHired(Date.from(hiredDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                selectedPersonal.setType((Type) typeCombo.getValue());
                selectedPersonal.setPrice(Float.parseFloat(priceText.getText().trim()));
                selectedPersonal.setQuantity(Long.parseLong(quantityText.getText().trim()));
                selectedPersonal.setEvent(ev);
                //se lo pasamos a la implementacion
                personalIface.updatePersonal(selectedPersonal, selectedPersonal.getPersonalresource_id());
                //volvemos a cargar la tabla
                tableLoad(ev.getEvent_id());

            }
        } catch (ConnectException e) {
            logger.severe("Error con el servidor");
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setContentText("Server error wait a few minutes");
            alerta.showAndWait();
        }
    }

    /**
     * Este metodo controla la creacion de nuevos personales
     *
     * @param action
     */
    private void handleCreateRequest(ActionEvent action) {
        logger.info("Metodo de crear");
        try {
            if (createComprobation()) {
                //Creamos el personal
                logger.info("Validaciones pasadas");
                PersonalResource jackson = new PersonalResource();
                //asignamos valores al personal
                jackson.setDateExpired(Date.from(expiredDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                jackson.setDateHired(Date.from(hiredDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                jackson.setType((Type) typeCombo.getValue());
                jackson.setPrice(Float.parseFloat(priceText.getText().trim()));
                jackson.setQuantity(Long.parseLong(quantityText.getText().trim()));
                jackson.setEvent(ev);
                //pasamos el objeto a la implementacion
                personalIface.createPersonal(jackson);
                //limpiamos los campos
                clearFields();
                //volvemos a cargar la tabla
                tableLoad(ev.getEvent_id());

            }
        } catch (ConnectException ex) {
            logger.severe("Error de conexion con el servidor");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Server error wait a few minutes");
            alert.showAndWait();
        }
    }

    /**
     * Este metdodo hace las validaciones del create
     *
     * @return
     */
    private boolean createComprobation() {
        logger.info("Validaciones create");
        try {//cantidad entero positivo
            if (quantityText.getText().matches("[0-9]{1,50}")) {
                //datehired<dateExpired
                if (hiredDate.getValue().isBefore(expiredDate.getValue()) | hiredDate.getValue().isEqual(expiredDate.getValue())) {
                    //precio formato 12.52
                    if (priceText.getText().trim().matches("[0-9]{1,9}[.][0-9]{1,9}")) {
                        //tipo no duplicado
                        if (validateTypeCreate((Type) typeCombo.getValue())) {

                            return true;
                        } else {
                            logger.severe("Tipo duplicado");
                            throw new PersonalDuplicatedException("The type cannot be duplicated");
                        }
                    } else {
                        logger.severe("Mal formato del precio");
                        throw new IncorrectPriceException("The price must be in this format 10.25");
                    }
                } else {
                    logger.severe("Datehired > a DateExpired");
                    throw new DateHiredIsAfterException("The date of the field date hired must be before the date of the field date expired");
                }
            } else {
                logger.severe("Cantidad tiene que ser un numero positivo");
                throw new IncorrectQuantityException("The quantity must be a positive number");
            }
        } catch (IncorrectQuantityException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
            logger.severe("Error quantityText");
        } catch (DateHiredIsAfterException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
            logger.severe("Datehired>dateExpired");
        } catch (IncorrectPriceException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
            logger.severe("Error priceText");
        } catch (PersonalDuplicatedException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
            logger.severe("Type duplicated");
        }
        return false;
    }

    /**
     * Este metodo limpia todos los campos
     */
    private void clearFields() {
        logger.info("Limpiar campos");
        expiredDate.setValue(null);
        hiredDate.setValue(null);
        priceText.setText("");
        quantityText.setText("");
        typeCombo.getSelectionModel().clearSelection();
    }

    /**
     * Este metodo habilita los botones si todos los campos estan informados
     */
    public void informedFields() {
        createbtn.disableProperty().bind(
                typeCombo.valueProperty().isNull().or(
                        expiredDate.valueProperty().isNull().or(
                                priceText.textProperty().isEmpty().or(
                                        quantityText.textProperty().isEmpty().or(
                                                hiredDate.valueProperty().isNull()))))
        );
    }

    /**
     * Este metodo valida que el tipo no sea duplicado a la hora de modificar
     *
     * @param type
     * @return
     */
    private boolean validateTypeModify(Type type) {
        logger.info("Validando tipo modificar");
        try {
            Collection<PersonalResource> per = personalIface.getPersonalByEvent(ev.getEvent_id());
            if (per.stream().filter(p -> p.getType().equals(type)).count() == 0 | this.p.getType().equals(typeCombo.getValue())) {
                return true;
            } else {
                return false;
            }
        } catch (ConnectException ex) {
            logger.severe("Error de conexion con el servidor");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Error de conexion con el servidor");
            alert.showAndWait();
        }
        return true;
    }

    /**
     * Este metodo valida que a la hora de crear no meta un tipo duplicado
     *
     * @param type
     * @return
     */
    private boolean validateTypeCreate(Type type) {
        logger.info("Validando tipo en el create");
        try {//Comprobamos que no esta el tipo ya en la tabla
            Collection<PersonalResource> per = personalIface.getPersonalByEvent(ev.getEvent_id());
            if (per.stream().filter(p -> p.getType().equals(type)).count() == 0) {
                return true;
            } else {

                return false;
            }
        } catch (ConnectException ex) {
            logger.severe("Error de conexion con el servidor");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Error de conexion con el servidor");
            alert.showAndWait();
        }
        return true;
    }

    /**
     * Este metodo hace las validaciones a la hora de modificar
     *
     * @return
     */
    private boolean modifyComprobation() {
        logger.info("Validando modificar");
        try {
            //cantidad entero positivo
            if (quantityText.getText().matches("[0-9]{1,50}")) {
                //precio formato 12.21
                if (priceText.getText().trim().matches("[0-9]{1,9}[.][0-9]{1,9}")) {
                    //dateHired<dateExpired
                    if (hiredDate.getValue().isBefore(expiredDate.getValue()) | hiredDate.getValue().isEqual(expiredDate.getValue())) {
                        //tipo no duplicado
                        if (validateTypeModify((Type) typeCombo.getValue())) {
                            return true;
                        } else {
                            logger.severe("Tipo duplicado");
                            throw new PersonalDuplicatedException("The type cannot be duplicated");

                        }
                    } else {
                        logger.severe("DateHired> a dateExpired");
                        throw new DateHiredIsAfterException("The date of the field date hired must be before the date of the field date expired");

                    }
                } else {
                    logger.severe("Error formato de price");
                    throw new IncorrectPriceException("The price must be in this format 10.25");

                }
            } else {
                logger.severe("Cantidad tiene que ser un entero positivo");
                throw new IncorrectQuantityException("The quantity must be a positive number");

            }
        } catch (IncorrectQuantityException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            logger.severe("Error quantityText");
        } catch (IncorrectPriceException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
            logger.severe("Error priceText");
        } catch (DateHiredIsAfterException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
            logger.severe("Datehired>dateExpired");
        } catch (PersonalDuplicatedException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
            logger.severe("Type duplicated");
        }
        return false;
    }
}

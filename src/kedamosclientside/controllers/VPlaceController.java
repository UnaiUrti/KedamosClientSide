/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kedamosclientside.controllers;

import java.io.IOException;
import java.net.ConnectException;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import kedamosclientside.entities.Event;
import kedamosclientside.entities.EventManager;
import kedamosclientside.entities.Place;
import kedamosclientside.exceptions.MaxCharacterException;
import kedamosclientside.exceptions.placeAddressBadException;
import kedamosclientside.exceptions.placeAddressExistException;
import kedamosclientside.exceptions.placeDateBadException;
import kedamosclientside.exceptions.placeNameBadException;
import kedamosclientside.exceptions.placePriceBadException;
import kedamosclientside.logic.PlaceFactory;
import kedamosclientside.logic.PlaceInterface;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import static org.hsqldb.Library.user;

/**
 * FXML Controller class
 *
 * @author UnaiUrtiaga
 */
public class VPlaceController {
    
    private final static Logger LOGGER = Logger.getLogger("kedamosclientside.controllers.VPlaceController");
    
    private Stage stage;
    private PlaceInterface placeInterface=PlaceFactory.getPlace();
    
    public void setPlace(PlaceInterface placeInterface) {
        this.placeInterface = placeInterface;
    }
    
    @FXML
    private TableView<Place> table;
    @FXML
    private TableColumn<Place, String> tcAddress;
    @FXML
    private TableColumn<Place, String> tcName;
    @FXML
    private TableColumn<Place, Float> tcPrice;
    @FXML
    private TableColumn<Place, ?> tcDateRenewal;
    @FXML
    private TextField tfAddress;
    @FXML
    private DatePicker dpDateRenewal;
    @FXML
    private TextField tfName;
    @FXML
    private Button btnCreate;
    @FXML
    private Label lblAddress;
    @FXML
    private Label lblPrice;
    @FXML
    private Label lblDateRenewal;
    @FXML
    private Label lblName;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnModify;
    @FXML
    private TextField tfPrice;
    @FXML
    private Button btnPrint;
    @FXML
    private Label lblPriceError;
    @FXML
    private Label lblDateError;
    @FXML
    private Label lblNameError;
    @FXML
    private Label lblAddressExists;
    @FXML
    private Label lblAddressError;
    
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
     * @param event
     */
    public void initStage(Parent root, Event event) {
        
        LOGGER.info("Generando ventana...");
        
        Scene scene = new Scene(root);
        stage = new Stage();
        //Set stage properties
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setTitle("Place");

        //La ventana no se podrá redimensionar
        stage.setResizable(false);

        //Los botones Create, Modify y Delete estarán deshabilitados
        btnCreate.setDisable(true);
        btnModify.setDisable(true);
        btnDelete.setDisable(true);

        //Los botones Print y Back estarán habilitados
        btnBack.setDisable(false);
        btnPrint.setDisable(false);

        //Los campos Address, Name, DateRenewal y Price estarán habilitados
        tfAddress.setDisable(false);
        tfName.setDisable(false);
        tfPrice.setDisable(false);
        dpDateRenewal.setDisable(false);
        
        tfAddress.setText("");
        tfName.setText("");
        tfPrice.setText("");
        dpDateRenewal.setValue(null);

        //Mensajes de error
        lblAddressError.setVisible(false);
        lblNameError.setVisible(false);
        lblPriceError.setVisible(false);
        lblDateError.setVisible(false);
        lblAddressExists.setVisible(false);

        //Se enfocará en el campo Address
        tfAddress.requestFocus();

        //La tabla mostrará todos los Places que existan
        tcAddress.setCellValueFactory(
                new PropertyValueFactory<>("address"));
        tcName.setCellValueFactory(
                new PropertyValueFactory<>("name"));
        tcPrice.setCellValueFactory(
                new PropertyValueFactory<>("price"));
        tcDateRenewal.setCellValueFactory(
                new PropertyValueFactory<>("dateRenewal"));
        updateTable();

        //Comprobar los campos Name y Address
        tfName.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                lblNameError.setVisible(false);
                tfName.setStyle("-fx-border-color: none;");

                //Comprobar que los campos Name estén informados
                if (tfName.getText().trim().equals("")) {
                    //En caso de que no lo estén los los botones Crear y Modificar se deshabilitarán
                    btnCreate.setDisable(true);
                    btnModify.setDisable(true);
                } else if (!tfAddress.getText().trim().equals("")) {
                    //En caso de que sí lo estén el botón Create se habilitará
                    btnCreate.setDisable(false);

                    //Si hay una fila seleccionada en la tabla se habilitará el botón Modify
                    if (!table.getSelectionModel().isEmpty()) {
                        btnModify.setDisable(false);
                    }
                    
                }
            }
        });
        tfAddress.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                lblAddressError.setVisible(false);
                lblAddressExists.setVisible(false);
                tfAddress.setStyle("-fx-border-color: none;");

                //Comprobar que los campos Address estén informados
                if (tfAddress.getText().trim().equals("")) {
                    //En caso de que no lo estén los los botones Crear y Modificar se deshabilitarán
                    btnCreate.setDisable(true);
                    btnModify.setDisable(true);
                } else if (!tfName.getText().trim().equals("")) {
                    //En caso de que sí lo estén el botón Create se habilitará
                    btnCreate.setDisable(false);

                    //Si hay una fila seleccionada en la tabla se habilitará el botón Modify
                    if (!table.getSelectionModel().isEmpty()) {
                        btnModify.setDisable(false);
                    }
                }
            }
        });
        tfPrice.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                lblPriceError.setVisible(false);
                tfPrice.setStyle("-fx-border-color: none;");
            }
            
        });
        dpDateRenewal.valueProperty().addListener((observable, oldValue, newValue) -> {
            lblDateError.setVisible(false);
            dpDateRenewal.setStyle("-fx-border-color: none;");
        });

        //Comprobar selección de la tabla
        table.getSelectionModel().selectedItemProperty().addListener(this::handleTableSelected);
        
        stage.setOnCloseRequest(this::handleCloseRequest);
        
        stage.show();
        
        LOGGER.info("Ventana generada correctamente");
        
    }
    
    @FXML
    private void handleCreatePlace(ActionEvent event) {
        
        try {
            LOGGER.info("Creando un Place...");
            
            Place place = new Place();
            place.setAddress(tfAddress.getText());
            
            validarCampos();

            //Comprobar que no exista ya un place con el Address introducido
            if (placeInterface.getPlaceByAddress(place) != null) {
                throw new placeAddressExistException("");
            }

            //Si no existe añadir un Place con todos los datos introducidos
            place.setName(tfName.getText());
            if (!tfPrice.getText().equals("")) {
                place.setPrice(Float.parseFloat(tfPrice.getText()));
            } else {
                place.setPrice(Float.valueOf(0));
            }
            if (dpDateRenewal.getValue() != null) {
                place.setDateRenewal(Date.from(dpDateRenewal.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            } else {
                place.setDateRenewal(null);
            }
            placeInterface.createPlace(place);

            //Vaciar todos los campos
            tfAddress.setText("");
            tfName.setText("");
            tfPrice.setText("");
            dpDateRenewal.setValue(null);

            //Actualizar la tabla con nuevos datos
            updateTable();
            
            LOGGER.info("Place creado correctamente");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success!");
            alert.setContentText("A place has been created successfully");
            alert.showAndWait();
            
        } catch (placePriceBadException ex) {
            LOGGER.severe("El valor del campo precio no es numerico");
            lblPriceError.setVisible(true);
            tfPrice.setStyle("-fx-border-color: red;");
        } catch (placeNameBadException ex) {
            LOGGER.severe("El valor del campo nombre no contiene solo letras");
            lblNameError.setVisible(true);
            tfName.setStyle("-fx-border-color: red;");
        } catch (placeDateBadException ex) {
            LOGGER.severe("El valor del campo fecha es posterior a la fecha actual");
            lblDateError.setVisible(true);
            dpDateRenewal.setStyle("-fx-border-color: red;");
        } catch (placeAddressBadException ex) {
            LOGGER.severe("El valor del campo address contiene caracteres no validos");
            lblAddressError.setVisible(true);
            tfAddress.setStyle("-fx-border-color: red;");
        } catch (placeAddressExistException ex) {
            LOGGER.severe("Ya existe un address con el valor introducido");
            lblAddressExists.setVisible(true);
            tfAddress.setStyle("-fx-border-color: red;");
        } catch (ConnectException ex) {
            LOGGER.severe("Ha habido un error a la hora de conectar con el servidor");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("CONNECTION ERROR");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        } catch (MaxCharacterException ex) {
            LOGGER.severe("Alguno de los campos Name o Address contiene mas de 255 caracteres");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("TOO MUCH CHARACTERS");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
        
    }
    
    @FXML
    private void handleDeletePlace(ActionEvent event) {
        
        LOGGER.info("Eliminando un Place...");
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Place");
        alert.setContentText("Are you sure you want to delete this Place?");
        Optional<ButtonType> answer = alert.showAndWait();
        if (answer.get() == ButtonType.OK) {
            
            try {
                //Si le da a SI eliminar el Place
                Place place = table.getSelectionModel().getSelectedItem();
                placeInterface.deletePlaceAlternative(place);

                //Vacíar los todos los campos
                tfAddress.setText("");
                tfName.setText("");
                tfPrice.setText("");
                dpDateRenewal.setValue(null);

                //Deseleccionar la tabla y actualizarla con los nuevos datos
                updateTable();
                
                LOGGER.info("Place eliminado correctamente");
            } catch (ConnectException ex) {
                LOGGER.severe("Ha habido un error a la hora de conectar con el servidor");
                Alert alert2 = new Alert(Alert.AlertType.WARNING);
                alert2.setTitle("CONNECTION ERROR");
                alert2.setContentText(ex.getMessage());
                alert2.showAndWait();
            }
            
        }
        
    }
    
    @FXML
    private void handleModifyPlace(ActionEvent event) throws placePriceBadException {
        
        try {
            LOGGER.info("Modificando un Place...");
            
            Place place = new Place();
            place.setAddress(tfAddress.getText());
            
            validarCampos();

            //Comprobar que el campo Address sea el mismo que el seleccionado en la tabla
            if (!tfAddress.getText().equals(table.getSelectionModel()
                    .getSelectedItem().getAddress()) && placeInterface.getPlaceByAddress(place) != null) {
                throw new placeAddressExistException("");
            }

            //Si coinciden, modificar los campos del Place seleccionado
            place.setName(tfName.getText());
            if (!tfPrice.getText().equals("")) {
                place.setPrice(Float.parseFloat(tfPrice.getText()));
            } else {
                place.setPrice(Float.valueOf(0));
            }
            if (dpDateRenewal.getValue() != null) {
                place.setDateRenewal(Date.from(dpDateRenewal.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            } else {
                place.setDateRenewal(null);
            }
            place.setId(table.getSelectionModel().getSelectedItem().getId());
            
            placeInterface.updatePlace(place);

            //Actualizar la tabla con los nuevos campos
            updateTable();

            //Vaciar todos los campos
            tfAddress.setText("");
            tfName.setText("");
            tfPrice.setText("");
            dpDateRenewal.setValue(null);
            
            LOGGER.info("Place modificado correctamente");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success!");
            alert.setContentText("A place has been modified successfully");
            alert.showAndWait();
            
        } catch (placePriceBadException ex) {
            LOGGER.severe("El valor del campo precio no es numerico");
            lblPriceError.setVisible(true);
            tfPrice.setStyle("-fx-border-color: red;");
        } catch (placeNameBadException ex) {
            LOGGER.severe("El valor del campo nombre no contiene solo letras");
            lblNameError.setVisible(true);
            tfName.setStyle("-fx-border-color: red;");
        } catch (placeDateBadException ex) {
            LOGGER.severe("El valor del campo fecha es posterior a la fecha actual");
            lblDateError.setVisible(true);
            dpDateRenewal.setStyle("-fx-border-color: red;");
        } catch (placeAddressBadException ex) {
            LOGGER.severe("El valor del campo address contiene caracteres no validos");
            lblAddressError.setVisible(true);
            tfAddress.setStyle("-fx-border-color: red;");
        } catch (placeAddressExistException ex) {
            LOGGER.severe("Ya existe un address con el valor introducido");
            lblAddressExists.setVisible(true);
            tfAddress.setStyle("-fx-border-color: red;");
        } catch (ConnectException ex) {
            LOGGER.severe("Ha habido un error a la hora de conectar con el servidor");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("CONNECTION ERROR");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        } catch (MaxCharacterException ex) {
            LOGGER.severe("Alguno de los campos Name o Address contiene mas de 255 caracteres");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("TOO MUCH CHARACTERS");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
        
    }
    
    @FXML
    private void handleBack(ActionEvent event) {
        try {
                    LOGGER.info("metodo boton back volver a event");
                    
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
    }
    
    @FXML
    private void handlePrintPlace(ActionEvent event) {
        LOGGER.info("Generando el report...");
        try {
            JasperReport report = JasperCompileManager.compileReport("src/kedamosclientside/report/PlaceReport.jrxml");
            JRBeanCollectionDataSource dataItems = new JRBeanCollectionDataSource((Collection<Place>) this.table.getItems());
            Map<String, Object> parameters = new HashMap<>();
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataItems);
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.setVisible(true);
        } catch (JRException ex) {
            System.out.println(ex.getMessage());
            LOGGER.severe("Ha habido un error al abrir el report");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Error during loading the report");
            alert.showAndWait();
        }
        
    }
    
    private void handleTableSelected(ObservableValue observable, Object oldValue, Object newValue) {
        LOGGER.info("Una fila de la tabla ha sido seleccionada o deseleccionada");
        //Si se ha seleccionado una fila informar todos los campos con los datos del Place seleccionado
        if (newValue != null) {
            LOGGER.info("Colocando la informacion de la fila seleccionada en los campos");
            Place place = (Place) newValue;
            tfAddress.setText(place.getAddress());
            tfName.setText(place.getName());
            tfPrice.setText(place.getPrice().toString());
            if (place.getDateRenewal() == null) {
                dpDateRenewal.setValue(null);
            } else {
                dpDateRenewal.setValue(place.getDateRenewal().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            }
            //Habilitar los botones Modify y Delete y deshabilitar el botón Create
            btnModify.setDisable(false);
            btnDelete.setDisable(false);
            btnCreate.setDisable(true);
            
        } else {
            LOGGER.info("Eliminando la información de la fila deseleccionada de los campos");
            //Si se ha deseleccionado una fila desinformar todos los campos
            tfAddress.setText("");
            tfName.setText("");
            tfPrice.setText("");
            dpDateRenewal.setValue(null);

            //Deshablitar los botones Create, Modify y Delete
            btnCreate.setDisable(true);
            btnModify.setDisable(true);
            btnDelete.setDisable(true);
        }
        
    }
    
    private void updateTable() {
        
        try {
            LOGGER.info("Actualizando los valores de la tabla");
            table.getItems().clear();
            Collection<Place> places = placeInterface.getAllPlaces();
            
            ObservableList<Place> placesForTable
                    = FXCollections.observableArrayList(places);
            
            table.setItems(placesForTable);
        } catch (Exception e) {
            
        }
    }
    
    private void handleCloseRequest(WindowEvent event) {
        LOGGER.info("Se ha enviado una petición para finalizar el programa");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("EXIT");
        
        alert.setContentText("Sure you want to go out?");
        
        Optional<ButtonType> answer = alert.showAndWait();
        if (answer.get() == ButtonType.OK) {
            LOGGER.info("El programa va a finalizar");
            stage.close();
        } else {
            LOGGER.info("La petición ha sido cancelada");
            event.consume();
        }
        
    }
    
    private void validarCampos() throws placePriceBadException, placeNameBadException, placeDateBadException, placeAddressBadException, MaxCharacterException {
        LOGGER.info("Comprobando la validez de los campos...");
        if (tfAddress.getText().getBytes().length >= 255) {
            throw new MaxCharacterException("Field Address cannot contain more than 255 characters");
        }
        if (tfName.getText().getBytes().length >= 255) {
            throw new MaxCharacterException("Field Name cannot contain more than 255 characters");
        }
        
        if (!tfAddress.getText().trim().toLowerCase().matches("[a-záéíóú]*[0-9]*[.]*[,]*")) {
            throw new placeAddressBadException("El campo Address solo puede contener numeros, letras, puntos y comas");
        }
        
        if (!tfPrice.getText().matches("[0-9]{1,5}[.][0-9]{1,2}") && !tfPrice.getText().matches("[0-9]{1,5}") && !tfPrice.getText().matches("")) {
            throw new placePriceBadException("El campo Price solo puede contener numeros");
        }
        
        if (!tfName.getText().trim().toLowerCase().matches("[a-zA-ZáéíóúÁÉÍÓÚ]{2,}[\\\\s[a-zA-ZáéíóúÁÉÍÓÚ]{2,}]*")) {
            throw new placeNameBadException("El campo Name solo puede contener letras");
        }
        
        if (dpDateRenewal.getValue() != null) {
            if (Date.from(dpDateRenewal.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()).after(Calendar.getInstance().getTime())) {
                throw new placeDateBadException("La fecha introducida no puede ser posterior a la fecha actual");
            }
        }
        LOGGER.info("Validez exitosa");
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kedamosclientside.controllers;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import kedamosclientside.logic.EventInterface;

/**
 * FXML Controller class
 *
 * @author Adrian Franco
 */
public class MyEventsViewController {

    private final static Logger logger = Logger.getLogger("client.controllers.MyEventsViewController");
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
    private ComboBox<Event> cmbCategory;
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
        
        try {
            Collection <Event> events = eventinterface.getEvents();
            ObservableList<Event> eventsForTable = FXCollections.observableArrayList(events);
            tvTable.setItems(eventsForTable);
        } catch (ClientLogicException ex) {
            Logger.getLogger(MyEventsViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        stage.show();
    }

    @FXML
    private void handleComboCategory(ActionEvent event) {
    }

    @FXML
    private void handleCreate(ActionEvent event) {
    }

    @FXML
    private void handleDelete(ActionEvent event) {
        logger.info("Boton para borrar Evento de la BD");
        
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Delete Event");
        alert.setContentText("Seguro que quieres eliminar el Evento?");
        Optional<ButtonType> resp = alert.showAndWait();
        if(resp.get()==ButtonType.OK){
            try {
                logger.info("Borrar evento");
                
                Event deleteEvent= tvTable.getSelectionModel().getSelectedItem();
                eventinterface.removeEvent(deleteEvent);
                
                tvTable.refresh();
            } catch (Exception ex) {
                Logger.getLogger(MyEventsViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void handleBack(ActionEvent event) {
    }

    @FXML
    private void handleModify(ActionEvent event) {
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

    private void handleCloseRequest(WindowEvent event) {

        logger.info("Se ha pulsado la opción de cerrar la ventana");

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("EXIT");

        alert.setContentText("Quieres cerrar la aplicacion?");

        Optional<ButtonType> resp = alert.showAndWait();

        if (resp.get() == ButtonType.OK) {
            logger.info("Se ha pulsado Confirm, el programa se va a cerrar");
            stage.close();
        } else {
            logger.info("Se ha cancelado la request, continuua en la misma ventana");
            event.consume();
        }
    }
}

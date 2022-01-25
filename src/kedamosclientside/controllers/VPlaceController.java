/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kedamosclientside.controllers;

import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
import kedamosclientside.entities.Event;
import kedamosclientside.entities.Place;
import kedamosclientside.logic.PlaceInterface;

/**
 * FXML Controller class
 *
 * @author UnaiUrtiaga
 */
public class VPlaceController {

    private Stage stage;
    private PlaceInterface placeInterface;

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
        btnDelete.setDisable(false);

        //Los botones Print y Back estarán habilitados
        btnBack.setDisable(false);
        btnPrint.setDisable(false);

        //Los campos Address, Name, DateRenewal y Price estarán habilitados
        tfAddress.setDisable(false);
        tfName.setDisable(false);
        tfPrice.setDisable(false);
        dpDateRenewal.setDisable(false);

        //Se enfocará en el campo Address
        tfAddress.requestFocus();
              
        //Comprobar los campos Name y Address
        tfName.setOnKeyTyped(this::handleTextChanged);
        tfAddress.setOnKeyTyped(this::handleTextChanged);
        tfPrice.setOnKeyTyped(this::handleTextChanged);
         
        //La tabla mostrará todos los Places que existan
        tcAddress.setCellValueFactory(
                new PropertyValueFactory<>("address"));
        tcName.setCellValueFactory(
                new PropertyValueFactory<>("name"));
        tcPrice.setCellValueFactory(
                new PropertyValueFactory<>("price"));
        tcDateRenewal.setCellValueFactory(
                new PropertyValueFactory<>("dateRenewal"));

        try {
            Collection<Place> places = placeInterface.getAllPlaces();

            ObservableList<Place> placesForTable
                    = FXCollections.observableArrayList(places);

            table.setItems(placesForTable);
        } catch (Exception e) {

        }

        stage.show();

    }

    @FXML
    private void handleCreatePlace(ActionEvent event) {

        //Comprobar que no exista ya un place con el Address introducido
        Place place = new Place();
        place.setAddress(tfAddress.getText());

        if (placeInterface.getPlaceByAddress(place) == null) {
            place.setName(tfName.getText());
            place.setPrice(Float.parseFloat(tfPrice.getText()));
            place.setDateRenewal(Date.from(dpDateRenewal.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));

            placeInterface.createPlace(place);
        }

    }

    @FXML
    private void handleDeletePlace(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Place");
        alert.setContentText("Are you sure you want to delete this Place?");
        Optional<ButtonType> answer = alert.showAndWait();
        if (answer.get() == ButtonType.OK) {
            try {
                Place place = table.getSelectionModel().getSelectedItem();

                placeInterface.deletePlaceAlternative(place);

                table.refresh();
            }catch(Exception e){
                
            }
        }

    }

    @FXML
    private void handleBack(ActionEvent event) {
    }

    @FXML
    private void handleModifyPlace(ActionEvent event) {
    }

    @FXML
    private void handlePrintPlace(ActionEvent event) {
    }

    @FXML
    private void handleTextChanged(KeyEvent event) {

        //Comprobar que los campos Address y Name estén informados
        if (tfName.getText().isEmpty() || tfAddress.getText().isEmpty()) {
            //En caso de que no lo estén los los botones Crear y Modificar se deshabilitarán
            btnCreate.setDisable(true);
            btnModify.setDisable(true);
        } else {
            //En caso de que sí lo estén el botón Create se habilitará
            btnCreate.setDisable(false);

            btnModify.setDisable(false);
        }

        if (tfPrice.getText().trim().contains("-")) {
            btnCreate.setDisable(true);
            btnModify.setDisable(true);
        } else {
            btnCreate.setDisable(true);
            btnModify.setDisable(true);
        }

    }

}

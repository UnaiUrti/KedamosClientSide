/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kedamosclientside.controllers;

import java.time.ZoneId;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
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
import kedamosclientside.entities.Place;
import kedamosclientside.exceptions.placeAddressExistException;
import kedamosclientside.exceptions.placeDateBadException;
import kedamosclientside.exceptions.placeNameBadException;
import kedamosclientside.exceptions.placePriceBadException;
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
    @FXML
    private Label lblAddressError;
    @FXML
    private Label lblPriceError;
    @FXML
    private Label lblDateError;
    @FXML
    private Label lblNameError;

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

        //Mensajes de error
        lblAddressError.setVisible(false);
        lblNameError.setVisible(false);
        lblPriceError.setVisible(false);
        lblDateError.setVisible(false);

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

    }

    @FXML
    private void handleCreatePlace(ActionEvent event) {

        try {
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

        } catch (placePriceBadException ex) {
            lblPriceError.setVisible(true);
            tfPrice.setStyle("-fx-border-color: red;");
        } catch (placeNameBadException ex) {
            lblNameError.setVisible(true);
            tfName.setStyle("-fx-border-color: red;");
        } catch (placeDateBadException ex) {
            lblDateError.setVisible(true);
            dpDateRenewal.setStyle("-fx-border-color: red;");
        } catch (placeAddressExistException ex) {
            lblAddressError.setVisible(true);
            tfAddress.setStyle("-fx-border-color: red;");
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
            } catch (Exception e) {

            }
        }

    }

    @FXML
    private void handleModifyPlace(ActionEvent event) throws placePriceBadException {

        Place place = new Place();
        place.setAddress(tfAddress.getText());
        boolean everythingCorrect = false;

        //Comprobar que el campo Address sea el mismo que el seleccionado en la tabla
        if (tfAddress.getText().equals(table.getSelectionModel()
                .getSelectedItem().getAddress()) || placeInterface.getPlaceByAddress(place) == null) {

            everythingCorrect = true;

        } else {
            lblAddressError.setVisible(true);
            tfAddress.setStyle("-fx-border-color: red;");
            everythingCorrect = false;
        }

        if (validarCampos()) {
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
        }

    }

    @FXML
    private void handleBack(ActionEvent event) {
    }

    @FXML
    private void handlePrintPlace(ActionEvent event) {
    }

    private void handleTableSelected(ObservableValue observable, Object oldValue, Object newValue) {

        //Si se ha seleccionado una fila informar todos los campos con los datos del Place seleccionado
        if (newValue != null) {
            Place place = (Place) newValue;
            tfAddress.setText(place.getAddress());
            tfName.setText(place.getName());
            tfPrice.setText(place.getPrice().toString());
            dpDateRenewal.setValue(place.getDateRenewal().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

            //Habilitar los botones Modify y Delete y deshabilitar el botón Create
            btnModify.setDisable(false);
            btnDelete.setDisable(false);
            btnCreate.setDisable(true);

        } else {
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
            table.getItems().clear();
            Collection<Place> places = placeInterface.getAllPlaces();

            ObservableList<Place> placesForTable
                    = FXCollections.observableArrayList(places);

            table.setItems(placesForTable);
        } catch (Exception e) {

        }
    }

    private void handleCloseRequest(WindowEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("EXIT");

        alert.setContentText("Sure you want to go out?");

        Optional<ButtonType> answer = alert.showAndWait();
        if (answer.get() == ButtonType.OK) {
            stage.close();
        } else {
            event.consume();
        }

    }

    private void validarCampos() throws placePriceBadException, placeNameBadException, placeDateBadException {

        if(!tfAddress.getText().toLowerCase().matches("[0-9]{1-}")){}
        
        if (!tfPrice.getText().matches("[0-9]{1,5}[.][0-9]{1,2}") && !tfPrice.getText().matches("[0-9]{1,5}") && !tfPrice.getText().matches("")) {
            throw new placePriceBadException("");
        }

        if (!tfName.getText().toLowerCase().matches("[a-z]{1,255}")) {
            throw new placeNameBadException("");
        }

        if (dpDateRenewal.getValue() != null) {
            if (Date.from(dpDateRenewal.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()).after(Calendar.getInstance().getTime())) {
                throw new placeDateBadException("");
            }
        }
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kedamosclientside.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Adrian Franco
 */
public class MyEventsViewController {

    private Stage stage;
    
    @FXML
    private TableView<?> tvTable;
    @FXML
    private TableColumn<?, ?> tcTable;
    @FXML
    private TableColumn<?, ?> tcDescription;
    @FXML
    private TableColumn<?, ?> tcPrice;
    @FXML
    private TableColumn<?, ?> tcCategory;
    @FXML
    private TableColumn<?, ?> tcDate;
    @FXML
    private TableColumn<?, ?> tcMinParticipants;
    @FXML
    private TableColumn<?, ?> tcMaxParticipants;
    @FXML
    private TableColumn<?, ?> tcActualParticipants;
    @FXML
    private TableColumn<?, ?> tcPlace;
    @FXML
    private TableColumn<?, ?> tcPersonal;
    @FXML
    private ComboBox<?> cmbCategory;
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

}

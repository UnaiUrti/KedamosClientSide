package kedamosclientside.controllers;


import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;

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
import kedamosclientside.entities.PersonalResource;
import kedamosclientside.entities.Type;

import kedamosclientside.logic.PersonalResourceImplementation;
import kedamosclientside.logic.PersonalResourceInterface;
import kedamosclientside.logic.PersonalResourceManager;


/**
 * Clase controladora de la ventana singUp.
 * @author Steven Arce, Irkus De La Fuente
 */
public class VPersonalResourceController  {
    
    private final static Logger logger = Logger.getLogger("client.controllers.VSignUpController");
    private Stage stage;
    private Tooltip tooltip;
    
    
    @FXML
    private Button printBtn,modifyBtn,backBtn,deleteBtn,createbtn;
    @FXML
    private DatePicker hiredDate,expiredDate;
    @FXML
    private TableView table;
    @FXML
    private ComboBox typeCombo;
    @FXML
    private TextField priceText,quantityText;
    @FXML
    private TableColumn typecolum,quantitycolum,pricecolum,expiredcolum,hiredcolum;
    
    private PersonalResourceInterface personalIface=new PersonalResourceImplementation()
            ;
   
    
    private ObservableList<PersonalResource> data;
    
    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    /**
     * Este metodo pretende inicializar el la escena y el escenario.
     * @param root nodo del grafo de la escena
     * @param id
     */
    public void initStage(Parent root,String id) {
        logger.info("Iniciado initStage de la ventana PersonalResource");
        Scene scene = new Scene(root);
       
        stage.setTitle("PersonalResource");
        stage.setResizable(false);
        modifyBtn.setDisable(true);
        deleteBtn.setDisable(true);
        typeCombo.getItems().addAll(Type.values());
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
            
            
                data=FXCollections.observableArrayList(personalIface.getPersonalByEvent(id));
       
            table.setItems(data);
          

       
            
        
        //  buttonBack.setOnAction(this::handleBack);
        //Controlador de evento para cerrar desde la barra de título
        stage.setOnCloseRequest(this::handleCloseRequest);

           
           
        stage.setScene(scene);
        stage.show();
    }

   /*
    private void handleBack(ActionEvent event) {
        logger.info("Se ha pulsado el boton back");
        stage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/controllers/VSignIn.fxml"));

        Parent root = null;
        try {
            root = (Parent) loader.load();
        } catch (IOException ex) {
            logger.info("Se ha producido un error al cargarel fxml de la ventana singIn");
        }

        VEventController controller = (VEventController) loader.getController();
        controller.setStage(stage);
        controller.initStage(root);

    }*/
    /**
     * Este metodo pretende controlar la salida de la ventana cuando se pulse la
     * X de la barra de titulo.
     * @param event representa la accion del evento handleCloseRequest
     */
    private void handleCloseRequest(WindowEvent event) {
        logger.info("Se ha pulsado la X de la barra de titulo y se enviara un "
                + "aviso de confirmacion");
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("EXIT");

        alert.setContentText("Seguro que quiere salir?");

        Optional<ButtonType> answer = alert.showAndWait();
        if (answer.get() == ButtonType.OK) {
            logger.info("Se ha pulsado OK y el programa va a finalizar");
            stage.close();
        } else {
            logger.info("Se ha cancelado el closeRequest");
            event.consume();
        }

    }

}

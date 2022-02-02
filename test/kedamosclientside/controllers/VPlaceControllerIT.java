/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kedamosclientside.controllers;

import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeoutException;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import kedamosclientside.Proba;
import kedamosclientside.entities.Place;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.runners.MethodSorters;
import org.testfx.api.FxAssert;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isInvisible;
import static org.testfx.matcher.base.NodeMatchers.isNull;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.ListViewMatchers.isEmpty;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;

/**
 *
 * @author UnaiUrtiaga
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VPlaceControllerIT extends ApplicationTest {

    private TextField tfAddress;
    private TextField tfName;
    private TextField tfPrice;
    private DatePicker dpDateRenewal;
    private TableView table;
    private Button btnCreate;
    private Button btnModify;
    private Button btnDelete;

    public VPlaceControllerIT() {
        tfAddress = lookup("#tfAddress").query();
        tfName = lookup("#tfName").query();
        tfPrice = lookup("#tfPrice").query();
        dpDateRenewal = lookup("#dpDateRenewal").query();
        btnCreate = lookup("#btnCreate").query();
        btnModify = lookup("#btnModify").query();
        btnDelete = lookup("#btnDelete").query();
        table = lookup("#table").queryTableView();
    }

    /*
    @Override
    public void start(Stage stage) throws Exception {
        //start JavaFX application to be tested    
        new Proba().start(stage);
        tfAddress = lookup("#tfAddress").query();
        tfName = lookup("#tfName").query();
        tfPrice = lookup("#tfPrice").query();
        dpDateRenewal = lookup("#dpDateRenewal").query();
        btnCreate = lookup("#btnCreate").query();
        btnModify = lookup("#btnModify").query();
        table = lookup("#table").queryTableView();
    }
     */
    @BeforeClass
    public static void setUpClass() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(Proba.class);

    }

    @Test
    public void testA_initialStage() {

        verifyThat(tfAddress, hasText(""));
        verifyThat("#lblAddressError", isInvisible());
        verifyThat("#lblAddressExists", isInvisible());
        verifyThat(tfName, hasText(""));
        verifyThat("#lblNameError", isInvisible());
        verifyThat(tfPrice, hasText(""));
        verifyThat("#lblPriceError", isInvisible());
        verifyThat(dpDateRenewal, (DatePicker dp) -> dp.getValue() == null);
        verifyThat("#lblDateError", isInvisible());
        verifyThat(btnCreate, isDisabled());
        verifyThat(btnModify, isDisabled());
        verifyThat(btnDelete, isDisabled());
        verifyThat("#btnPrint", isEnabled());
        verifyThat("#btnBack", isEnabled());

    }

    //@Ignore
    @Test
    public void testB_CRUDButtonsActivation() {

        verifyThat("#btnCreate", isDisabled());
        verifyThat("#btnModify", isDisabled());
        verifyThat("#btnDelete", isDisabled());

        //Verificar que no se activan si sólo está el campo Address informado
        clickOn("#tfAddress");
        write("Wachin");

        sleep(500);

        verifyThat("#btnCreate", isDisabled());
        verifyThat("#btnModify", isDisabled());
        verifyThat("#btnDelete", isDisabled());

        sleep(500);

        //Verificar que no se activan si sólo está el campo Name informado
        /*clickOn("#tfAddress");
        push(KeyCode.CONTROL, KeyCode.A);
        eraseText(1);*/
        tfAddress.clear();

        clickOn("#tfName");
        write("Wachin");

        sleep(500);

        verifyThat("#btnCreate", isDisabled());
        verifyThat("#btnModify", isDisabled());
        verifyThat("#btnDelete", isDisabled());

        sleep(500);

        /*
        * Verificar que sólo se activa el botón Create cuando los dos campos
        * obligatorios están informados
         */
        clickOn("#tfAddress");
        write("Wachin");

        sleep(500);

        verifyThat("#btnCreate", isEnabled());
        verifyThat("#btnModify", isDisabled());
        verifyThat("#btnDelete", isDisabled());

        sleep(500);

        /*
        * Verificar que los botones Modify y Delete se activen cuando la tabla
        * es seleccionada
         */
        assertNotEquals("FAIL - Table has no data",
                table.getItems().size(), 0);
        Node row = lookup(".table-row-cell").nth(0).query();
        assertNotNull("FAIL - Table has not that row. ", row);
        clickOn(row);

        sleep(500);

        verifyThat("#btnModify", isEnabled());
        verifyThat("#btnDelete", isEnabled());
        verifyThat("#btnCreate", isDisabled());

        //Borrar todos los campos antes de ir a la segunda prueba
        tfAddress.clear();
        tfName.clear();
        tfPrice.clear();
        dpDateRenewal.setValue(null);

        sleep(1000);

    }

    @Ignore
    @Test
    public void testC_FieldsValidation() {
        
        clickOn(tfAddress);
        write("abcd*1234");
        
        clickOn(tfName);
        write("abcd*1234");
        
        clickOn(tfPrice);
        write("abcd*1234");
        
        assertNotEquals("FAIL - Button is not enabled",
                btnCreate.isDisabled());
        
        clickOn(btnCreate);
        
        
    }

    @Test
    public void testD_Create() {

        int rowCount = table.getItems().size();

        String address = "Address" + (rowCount + 1);

        clickOn("#tfAddress");
        write(address);

        clickOn("#tfName");
        write("ExampleName");

        sleep(500);

        assertNotEquals("FAIL - Button is not enabled",
                btnCreate.isDisabled());

        sleep(500);

        clickOn("#btnCreate");

        verifyThat("A place has been created successfully", isVisible());

        clickOn("Aceptar");

        assertEquals("FAIL - A row has not been added", rowCount + 1,
                table.getItems().size());

        List<Place> places = table.getItems();
        assertEquals("FAIL - The place has not been added",
                places.stream().filter(u -> u.getAddress().equals(address)).count(), 1);

    }

    @Test
    public void testE_CreateExists() {

        int rowCount = table.getItems().size();

        String existingAddress = ((Place) table.getItems().get(0)).getAddress();

        clickOn(tfAddress);
        write(existingAddress);

        clickOn(tfName);
        write("ExampleName");

        assertNotEquals("FAIL - Button is not enabled",
                btnCreate.isDisabled());

        clickOn(btnCreate);
        sleep(500);
        verifyThat("#lblAddressExists", isVisible());

        assertEquals("FAIL - A row has been added", rowCount,
                table.getItems().size());

    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kedamosclientside.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeoutException;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import org.junit.Test;
import javax.jnlp.UnavailableServiceException;
import kedamosclientside.Client;
import kedamosclientside.entities.PersonalResource;
import kedamosclientside.entities.Type;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isNull;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.ButtonMatchers.isCancelButton;
import static org.testfx.matcher.control.ButtonMatchers.isDefaultButton;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;

/**
 *
 * @author Irkus de la fuente
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VPersonalResourceControllerTest extends ApplicationTest {

    private Button btnCrear;
    private Button btnModify;
    private Button btnDelete;
    private Button btnBack;
    private TextField tfQuantity, tfPrice;
    private ComboBox typeCombo;
    private DatePicker dateHired, dateExpired;
    private TableView table;

    /**
     * Iniciamos el main
     *
     * @throws TimeoutException
     * @throws UnavailableServiceException
     */
    public VPersonalResourceControllerTest() throws TimeoutException, UnavailableServiceException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(Client.class);

        typeCombo = lookup("#typeCombo").queryComboBox();
        dateHired = lookup("#hiredDate").query();
        dateExpired = lookup("#expiredDate").query();
        table = lookup("#table").query();

    }

    /**
     * Comprobamos el estado incial de la ventana
     */
    @Test
    public void testA_initialStatus() {

        verifyThat("#createbtn", isDisabled());
        verifyThat("#modifyBtn", isDisabled());
        verifyThat("#deleteBtn", isDisabled());
        verifyThat("#quantityText", hasText(""));
        verifyThat("#priceText", hasText(""));
        int comboItems = typeCombo.getItems().size();
        Assert.assertEquals("No se ha cargado bien la comboBox", comboItems, 13);
        verifyThat("hiredDate", isNull());
        verifyThat("expiredDate", isNull());
    }

    /**
     * Comprobamos que los campos esten vacios y vaciamos la tabla si hay filas
     */
    @Test
    public void testB_informedFields() {
        int rowCount = table.getItems().size();
        for (int i = 0; i < rowCount; i++) {
            Node row = lookup(".table-row-cell").nth(0).query();
            clickOn(row);
            clickOn("#deleteBtn");
            clickOn(isDefaultButton());

        }
        //Combo Vacia
        clickOn("#quantityText");
        write("2");
        clickOn("#priceText");
        write("1.2");
        write("payaso");
        dateHired.setValue(LocalDate.now());
        dateExpired.setValue(LocalDate.now());

        verifyThat("#createbtn", isDisabled());
        clickOn("#typeCombo");
        press(KeyCode.DOWN).release(KeyCode.DOWN);
        press(KeyCode.ENTER).release(KeyCode.ENTER);
        //dateHired vacio
        dateHired.setValue(null);
        verifyThat("#createbtn", isDisabled());
        dateHired.setValue(LocalDate.now());
        //dateExpired Vacio
        dateExpired.setValue(null);
        verifyThat("#createbtn", isDisabled());
        dateExpired.setValue(LocalDate.now());
        //Price vacio
        clickOn("#priceText");
        push(KeyCode.CONTROL, KeyCode.A);
        eraseText(1);
        verifyThat("#createbtn", isDisabled());
        clickOn("#quantityText");
        write("2");
        //campo quantity vacio
        clickOn("#quantityText");
        push(KeyCode.CONTROL, KeyCode.A);
        eraseText(1);
        verifyThat("#createbtn", isDisabled());
        clearFields();

    }

    /**
     * Comprobamos error de price y quantity incorrectos en el create
     */
    @Test
    public void testC_IncorrectFormatCreate() {
        //formato de price incorrecto
        int rowCount = table.getItems().size();
        clickOn("#quantityText");
        write("1");
        clickOn("#priceText");
        write(".");
        clickOn("#typeCombo");
        press(KeyCode.DOWN).release(KeyCode.DOWN);
        press(KeyCode.ENTER).release(KeyCode.ENTER);
        String type = typeCombo.getValue().toString();
        dateHired.setValue(LocalDate.now());
        dateExpired.setValue(LocalDate.now());
        clickOn("#createbtn");
        verifyThat("The price must be in this format 10.25", isVisible());
        sleep(1000);
        clickOn("Aceptar");
        //meter espacios en quantity
        clickOn("#priceText");
        push(KeyCode.CONTROL, KeyCode.A);
        write("1.2");
        clickOn("#quantityText");
        push(KeyCode.CONTROL, KeyCode.A);
        write("         ");
        clickOn("#createbtn");
        verifyThat("The quantity must be a positive number", isVisible());
        sleep(1000);
        clickOn("Aceptar");
        List<PersonalResource> users = table.getItems();
        assertEquals("The user has not been added!!!",
                users.stream().filter(u -> u.getType().toString().equals(type)).count(), 0);

    }

    /**
     * Comprobamos error dateHired>dateExpired en el create
     */
    @Test
    public void testD_dateHiredMayorCreate() {
        int rowCount = table.getItems().size();
        clearFields();
        clickOn("#quantityText");
        write("1");
        clickOn("#priceText");
        write("13.2");
        clickOn("#typeCombo");
        press(KeyCode.DOWN).release(KeyCode.DOWN);
        press(KeyCode.ENTER).release(KeyCode.ENTER);
        String type = typeCombo.getValue().toString();
        //DateHired>DateExpired
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        dateHired.setValue(LocalDate.parse("2001-01-01", formatter));
        dateExpired.setValue(LocalDate.parse("2000-01-01", formatter));
        clickOn("#createbtn");
        verifyThat("The date of the field date hired must be before the date of the field date expired", isVisible());
        sleep(1000);
        clickOn("Aceptar");
        List<PersonalResource> users = table.getItems();
        assertEquals("The user has not been added!!!",
                users.stream().filter(u -> u.getType().toString().equals(type)).count(), 0);

    }

    /**
     * Comprobamos que crea correctamente
     */
    @Test
    public void testE_createCorrect() {

        clearFields();
        int rowCount = table.getItems().size();
        clickOn("#quantityText");
        write("1");
        clickOn("#priceText");
        write("13.2");
        clickOn("#typeCombo");
        press(KeyCode.DOWN).release(KeyCode.DOWN);
        press(KeyCode.ENTER).release(KeyCode.ENTER);
        String type = typeCombo.getValue().toString();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        dateExpired.setValue(LocalDate.parse("2001-01-01", formatter));
        dateHired.setValue(LocalDate.parse("2000-01-01", formatter));
        clickOn("#createbtn");
        List<PersonalResource> users = table.getItems();
        assertEquals("The user has not been added!!!",
                users.stream().filter(u -> u.getType().toString().equals(type)).count(), 1);

    }

    /**
     * Comrpbamos error tipo duplicado en el create
     */
    @Test
    public void testF_createduplicate() {
        clearFields();
        int rowCount = table.getItems().size();
        clickOn("#quantityText");
        write("1");
        clickOn("#priceText");
        write("13.2");
        clickOn("#typeCombo");
        press(KeyCode.DOWN).release(KeyCode.DOWN);
        press(KeyCode.ENTER).release(KeyCode.ENTER);
        String type = typeCombo.getValue().toString();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        dateExpired.setValue(LocalDate.parse("2001-01-01", formatter));
        dateHired.setValue(LocalDate.parse("2000-01-01", formatter));
        clickOn("#createbtn");
        List<PersonalResource> users = table.getItems();
        verifyThat("The type cannot be duplicated", isVisible());
        clickOn("Aceptar");
        assertEquals("The user has  been added!!!",
                users.stream().filter(u -> u.getType().toString().equals(type)).count(), 1);
    }

    /**
     * Comprobamos que no borra cuando cancelas el delete
     */
    @Test
    public void testG_deleteCancel() {
        int rowCount = table.getItems().size();
        assertNotEquals("Table has no data: Cannot test.",
                rowCount, 0);
        //Clickamos la primera fila si la hay
        Node row = lookup(".table-row-cell").nth(0).query();
        assertNotNull("Row is null: table has not that row. ", row);
        clickOn(row);
        verifyThat("#deleteBtn", isEnabled());//note that id is used instead of fx:id
        clickOn("#deleteBtn");
        verifyThat("Do you really want to delete this personal?",
                isVisible());
        clickOn(isCancelButton());
        assertEquals("A row has been deleted!!!", rowCount, table.getItems().size());
    }

    /**
     * Comprobamos que borra cuando aceptas el delete
     */
    @Test
    public void testH_deleteCorrect() {
        int rowCount = table.getItems().size();
        assertNotEquals("Table has no data: Cannot test.",
                rowCount, 0);
        //Clickamos la primera fila si la hay
        Node row = lookup(".table-row-cell").nth(0).query();
        assertNotNull("Row is null: table has not that row. ", row);
        clickOn(row);
        verifyThat("#deleteBtn", isEnabled());
        clickOn("#deleteBtn");
        verifyThat("Do you really want to delete this personal?",
                isVisible());
        clickOn(isDefaultButton());
        assertEquals("The row has not been deleted!!!",
                rowCount - 1, table.getItems().size());

    }

    /**
     * Comprobamos el error de tipo duplicado en el modificar
     */
    @Test
    public void testI_modifyduplicated() {
        clickOn("#quantityText");
        write("1");
        clickOn("#priceText");
        write("13.2");
        clickOn("#typeCombo");
        press(KeyCode.DOWN).release(KeyCode.DOWN);
        press(KeyCode.ENTER).release(KeyCode.ENTER);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        dateExpired.setValue(LocalDate.parse("2001-01-01", formatter));
        dateHired.setValue(LocalDate.parse("2000-01-01", formatter));
        clickOn("#createbtn");

        clickOn("#quantityText");
        write("1");
        clickOn("#priceText");
        write("13.2");
        clickOn("#typeCombo");
        press(KeyCode.DOWN).release(KeyCode.DOWN);
        press(KeyCode.DOWN).release(KeyCode.DOWN);
        press(KeyCode.ENTER).release(KeyCode.ENTER);
        dateExpired.setValue(LocalDate.parse("2001-01-01", formatter));
        dateHired.setValue(LocalDate.parse("2000-01-01", formatter));
        clickOn("#createbtn");

        int rowCount = table.getItems().size();
        assertNotEquals("Table has no data: Cannot test.",
                rowCount, 0);
        //clickamos la primera fila si la hay
        Node row = lookup(".table-row-cell").nth(0).query();
        assertNotNull("Row is null: table has not that row. ", row);
        clickOn(row);
        verifyThat("#modifyBtn", isEnabled());
        clickOn("#typeCombo");
        press(KeyCode.DOWN).release(KeyCode.DOWN);
        press(KeyCode.ENTER).release(KeyCode.ENTER);
        clickOn("#modifyBtn");
        verifyThat("The type cannot be duplicated", isVisible());
        clickOn("Aceptar");
        List<PersonalResource> users = table.getItems();
        assertEquals("The user has  been added!!!",
                users.stream().filter(u -> u.getType().equals(Type.SEGURIDAD)).count(), 1);

    }

    /**
     * Comprobamos error quantity y price en el modificar
     */
    @Test
    public void testJ_IncorrectFormatModify() {
        int rowCount = table.getItems().size();
        PersonalResource selectedPersonal;
        Node row;
        assertNotEquals("Table has no data: Cannot test.",
                rowCount, 0);
        //look for 1st row in table view and click it
        row = lookup(".table-row-cell").nth(0).query();
        assertNotNull("Row is null: table has not that row. ", row);
        clickOn(row);
        selectedPersonal = (PersonalResource) table.getSelectionModel()
                .getSelectedItem();
        clickOn("#priceText");
        push(KeyCode.CONTROL, KeyCode.A);
        write(".");
        clickOn("#modifyBtn");
        verifyThat("The price must be in this format 10.25", isVisible());
        sleep(1000);
        clickOn("Aceptar");
        assertEquals("The user has been modified!!!",
                selectedPersonal, (PersonalResource) table.getSelectionModel().getSelectedItem());
        clickOn("#priceText");
        push(KeyCode.CONTROL, KeyCode.A);
        write("1.2");
        clickOn("#quantityText");
        push(KeyCode.CONTROL, KeyCode.A);
        write("         ");
        clickOn("#modifyBtn");
        verifyThat("The quantity must be a positive number", isVisible());
        sleep(1000);
        clickOn("Aceptar");

        assertEquals("The user has been modified!!!",
                selectedPersonal, (PersonalResource) table.getSelectionModel().getSelectedItem());
    }

    /**
     * Comprobamos error dateHired>dateExpired en el modifcar
     */
    @Test
    public void testK_modifyDateIncorrect() {
        int rowCount = table.getItems().size();
        PersonalResource selectedPersonal;
        Node row;
        assertNotEquals("Table has no data: Cannot test.",
                rowCount, 0);
        //clickamos la primera fila si la hay
        row = lookup(".table-row-cell").nth(0).query();
        assertNotNull("Row is null: table has not that row. ", row);
        clickOn(row);
        selectedPersonal = (PersonalResource) table.getSelectionModel()
                .getSelectedItem();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        dateHired.setValue(LocalDate.parse("2001-01-01", formatter));
        dateExpired.setValue(LocalDate.parse("2000-01-01", formatter));
        clickOn("#modifyBtn");
        verifyThat("The date of the field date hired must be before the date of the field date expired", isVisible());
        sleep(1000);
        clickOn("Aceptar");

        assertEquals("The user has been modified!!!",
                selectedPersonal, (PersonalResource) table.getSelectionModel().getSelectedItem());
    }

    /**
     * Comprobamos que modifica correctamente y limpiamos la tabla
     */
    @Test
    public void testM_modifyCorrect() {
        int rowCount = table.getItems().size();
        PersonalResource selectedPersonal;
        Node row;
        assertNotEquals("Table has no data: Cannot test.",
                rowCount, 0);
        //clickar la primera fila si la hay
        row = lookup(".table-row-cell").nth(0).query();
        assertNotNull("Row is null: table has not that row. ", row);
        clickOn(row);
        selectedPersonal = (PersonalResource) table.getSelectionModel()
                .getSelectedItem();
        clickOn("#priceText");
        push(KeyCode.CONTROL, KeyCode.A);
        write("1.2");
        clickOn("#quantityText");
        push(KeyCode.CONTROL, KeyCode.A);
        write("5");
        clickOn("#modifyBtn");

        assertNotEquals("The user has been modified!!!",
                selectedPersonal, (PersonalResource) table.getSelectionModel().getSelectedItem());
        clickOn(row);
        clickOn("#deleteBtn");
        clickOn(isDefaultButton());
        clickOn(row);
        clickOn("#deleteBtn");
        clickOn(isDefaultButton());
    }

    private void clearFields() {

        clickOn("#quantityText");
        push(KeyCode.CONTROL, KeyCode.A);
        eraseText(1);
        clickOn("#priceText");
        push(KeyCode.CONTROL, KeyCode.A);
        eraseText(1);
        dateHired.setValue(null);
        dateExpired.setValue(null);

    }

}

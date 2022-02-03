/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kedamosclientside.controllers;

import com.lowagie.text.pdf.TextField;
import java.awt.Button;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeoutException;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import kedamosclientside.KedamosApp;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;
import org.junit.Ignore;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isInvisible;
import static org.testfx.matcher.base.NodeMatchers.isNull;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.ButtonMatchers.isCancelButton;
import static org.testfx.matcher.control.ButtonMatchers.isDefaultButton;
import static org.testfx.matcher.control.ComboBoxMatchers.hasSelectedItem;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import kedamosclientside.entities.EventManager;
import kedamosclientside.entities.UserStatus;
import static kedamosclientside.entities.UserStatus.ENABLED;

/**
 * Este clase representa el test para validar la funcionalidad de la ventana
 * del admin.
 * 
 * @author Steven Arce
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VUserManagementControllerIT extends ApplicationTest {

    private TableView table;

    @BeforeClass
    public static void setUpClass() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(KedamosApp.class);
    }

    @Test
    public void testA_AdminLogin() {
        clickOn("#txtUsername");
        write("admin");
        clickOn("#txtPassword");
        write("admin");
        clickOn("#btnSignIn");
        verifyThat("#UserManagementPane", isVisible());
    }

    @Test
    public void testB_initialStage() {

        verifyThat("#txtUsername", hasText(""));
        verifyThat("#txtFullName", hasText(""));
        verifyThat("#txtPassword", hasText(""));
        verifyThat("#txtEmail", hasText(""));
        verifyThat("#cbStatus", (ComboBox cb) -> cb.getSelectionModel().getSelectedIndex() == -1);
        verifyThat("#cbManagerCategory", (ComboBox cb) -> cb.getSelectionModel().getSelectedIndex() == -1);
        verifyThat("#dpLastPasswordChange", (DatePicker dp) -> dp.getValue() == null);

        verifyThat("#btnCreate", isEnabled());
        verifyThat("#btnModify", isDisabled());
        verifyThat("#btnDelete", isDisabled());
        verifyThat("#btnPrint", isEnabled());
        verifyThat("#btnLogOut", isEnabled());

        verifyThat("#lblUsername", isInvisible());
        verifyThat("#lblFullName", isInvisible());
        verifyThat("#lblEmail", isInvisible());
        verifyThat("#lblPassword", isInvisible());
        verifyThat("#lblStatus", isInvisible());
        verifyThat("#lblManagerCategory", isInvisible());
        verifyThat("#lblLastPasswordChange", isInvisible());

    }

    @Test
    public void testC_btnCreateIsDisabledOnFieldsChange() {

        table = lookup("#tlView").queryTableView();
        assertNotEquals("FAIL - Table has no data",
                table.getItems().size(), 0);
        Node row = lookup(".table-row-cell").nth(0).query();
        assertNotNull("FAIL - Table has not that row. ", row);
        clickOn(row);
        verifyThat("#btnModify", isEnabled());
        verifyThat("#btnDelete", isEnabled());
        verifyThat("#btnCreate", isDisabled());
        press(KeyCode.CONTROL);
        clickOn(row);
        sleep(1000);
    }

    @Test
    public void testD_createEmpty() {
        clickOn("#btnCreate");
        verifyThat("#lblUsername", isVisible());
        verifyThat("#lblFullName", isVisible());
        verifyThat("#lblEmail", isVisible());
        verifyThat("#lblPassword", isVisible());
        verifyThat("#lblStatus", isVisible());
        verifyThat("#lblManagerCategory", isVisible());
        sleep(1000);
    }

    @Test
    public void testE_createWithIncorrectEmail() {

        String username = "username" + new Random().nextInt(250 - 1 + 1) + 1;

        clickOn("#txtUsername");
        write(username);
        clickOn("#txtFullName");
        write("test");
        clickOn("#txtPassword");
        write("abcd*1234");
        clickOn("#txtEmail");
        write("/test/@gmail.com");
        clickOn("#cbStatus");
        push(KeyCode.CONTROL, KeyCode.DOWN);
        push(KeyCode.ENTER);
        clickOn("#cbManagerCategory");
        push(KeyCode.CONTROL, KeyCode.DOWN);
        push(KeyCode.ENTER);

        clickOn("#btnCreate");

        verifyThat("#lblEmail", isVisible());
        sleep(1000);
    }

    @Test
    public void testF_createWithExistingEmail() {

        //String username = "username" + new Random().nextInt();
        //String email = "email"+new Random().nextInt()+"@gmail.com";  
        table = lookup("#tlView").queryTableView();
        String email = ((EventManager) table.getItems().get(0)).getEmail();

        clickOn("#txtEmail");
        push(KeyCode.CONTROL, KeyCode.A);
        write(email);
        clickOn("#btnCreate");

        verifyThat("#lblEmail", isVisible());
        sleep(1000);
    }

    @Test
    public void testG_createWithExistingUsername() {

        //String username = "username" + new Random().nextInt();
        String email = "email" + new Random().nextInt(250 - 1 + 1) + 1 + "@gmail.com";
        clickOn("#txtEmail");
        push(KeyCode.CONTROL, KeyCode.A);
        write(email);

        table = lookup("#tlView").queryTableView();
        String username = ((EventManager) table.getItems().get(0)).getUsername();
        clickOn("#txtUsername");
        push(KeyCode.CONTROL, KeyCode.A);
        write(username);
        clickOn("#btnCreate");

        verifyThat("#lblUsername", isVisible());

        sleep(1000);
    }

    @Test
    public void testH_createEventManager() {
        table = lookup("#tlView").queryTableView();
        int rowCount = table.getItems().size();

        String username = "username" + new Random().nextInt(250 - 1 + 1) + 1;

        clickOn("#txtUsername");
        push(KeyCode.CONTROL, KeyCode.A);
        write(username);

        String email = "email" + new Random().nextInt(250 - 1 + 1) + 1 + "@gmail.com";
        clickOn("#txtEmail");
        push(KeyCode.CONTROL, KeyCode.A);
        write(email);

        clickOn("#btnCreate");
        clickOn("Aceptar");

        assertEquals("The row has not been added!!!", rowCount + 1, table.getItems().size());

        List<EventManager> users = table.getItems();
        assertEquals("The user has not been added!!!",
                users.stream().filter(e -> e.getUsername().equals(username)).count(), 1);
        assertEquals("The user has not been added!!!",
                users.stream().filter(e -> e.getEmail().equals(email)).count(), 1);
        sleep(1000);
    }

    @Test
    public void testI_ediEventManagerWithExistingUsername() {
        table = lookup("#tlView").queryTableView();
        int rowCount = table.getItems().size();
        assertNotEquals("Table has no data: Cannot test.",
                rowCount, 0);
        Node row = lookup(".table-row-cell").nth(0).query();
        assertNotNull("FAIL - Table has not that row. ", row);
        clickOn(row);

        EventManager selectedEventManager = (EventManager) table.getSelectionModel()
                .getSelectedItem();

        String username = ((EventManager) table.getItems()
                .get(table.getItems().size() - 1))
                .getUsername();

        clickOn("#txtUsername");
        push(KeyCode.CONTROL, KeyCode.A);
        write(username);

        clickOn("#txtFullName");
        push(KeyCode.CONTROL, KeyCode.A);
        write("example test");

        clickOn("#btnModify");

        verifyThat("#lblUsername", isVisible());
        press(KeyCode.CONTROL);
        clickOn(row);

        sleep(1000);
    }

    @Test
    public void testJ_ediEventManagerWithExistingEmail() {
        table = lookup("#tlView").queryTableView();
        int rowCount = table.getItems().size();
        assertNotEquals("Table has no data: Cannot test.",
                rowCount, 0);
        Node row = lookup(".table-row-cell").nth(0).query();
        assertNotNull("FAIL - Table has not that row. ", row);
        clickOn(row);

        EventManager selectedEventManager = (EventManager) table.getSelectionModel()
                .getSelectedItem();

        String email = ((EventManager) table.getItems()
                .get(table.getItems().size() - 1))
                .getEmail();

        clickOn("#txtEmail");
        push(KeyCode.CONTROL, KeyCode.A);
        write(email);

        clickOn("#txtFullName");
        push(KeyCode.CONTROL, KeyCode.A);
        write("example test");

        clickOn("#btnModify");

        verifyThat("#lblEmail", isVisible());
        press(KeyCode.CONTROL);
        clickOn(row);

        sleep(1000);
    }

    @Test
    public void testK_ediEventManager() {
        table = lookup("#tlView").queryTableView();
        int rowCount = table.getItems().size();
        assertNotEquals("Table has no data: Cannot test.",
                rowCount, 0);
        Node row = lookup(".table-row-cell").nth(0).query();
        assertNotNull("FAIL - Table has not that row. ", row);
        clickOn(row);

        EventManager selectedEventManager = (EventManager) table.getSelectionModel()
                .getSelectedItem();

        int selectedIndex = table.getSelectionModel().getSelectedIndex();

        EventManager eventManagerModified = selectedEventManager;

        eventManagerModified.setFullName("Edit test" + new Random().nextInt(80 - 1 + 1));

        clickOn("#txtFullName");
        push(KeyCode.CONTROL, KeyCode.A);
        write(eventManagerModified.getFullName());

        clickOn("#cbStatus");
        if (selectedEventManager.getStatus() == UserStatus.ENABLED) {
            push(KeyCode.CONTROL, KeyCode.DOWN);
            push(KeyCode.ENTER);
            eventManagerModified.setStatus(UserStatus.DISABLED);
        } else {
            push(KeyCode.CONTROL, KeyCode.UP);
            push(KeyCode.ENTER);
            eventManagerModified.setStatus(UserStatus.ENABLED);
        }

        String username = "EditUsername" + new Random().nextInt(50 - 1 + 1);
        clickOn("#txtUsername");
        push(KeyCode.CONTROL, KeyCode.A);
        write(username);
        eventManagerModified.setUsername(username);

        clickOn("#btnModify");
        clickOn("Aceptar");

        assertEquals("The user has not been modified!!!",
                eventManagerModified,
                (EventManager) table.getItems().get(selectedIndex));

        sleep(1000);
    }

    @Test
    public void testL_removeEventManager() {
        table = lookup("#tlView").queryTableView();
        int rowCount = table.getItems().size();
        assertNotEquals("Table has no data: Cannot test.",
                rowCount, 0);

        Node row = lookup(".table-row-cell").nth(0).query();
        assertNotNull("Row is null: table has not that row. ", row);
        clickOn(row);

        clickOn("#btnDelete");   
        clickOn("Aceptar");
        assertEquals("The row has not been deleted!!!",
                rowCount - 1, table.getItems().size());

        sleep(1000);
    }

}

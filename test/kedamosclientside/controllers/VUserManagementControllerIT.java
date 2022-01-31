/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kedamosclientside.controllers;

import com.lowagie.text.pdf.TextField;
import java.awt.Button;
import java.util.concurrent.TimeoutException;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
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

/**
 *
 * @author Steven Arce
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VUserManagementControllerIT extends ApplicationTest {

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
    public void testC_AdminLogin() {
        clickOn("#txtUsername");
        write("admin");
        clickOn("#txtPassword");
        write("admin");
        clickOn("#btnSignIn");
        verifyThat("#UserManagementPane", isVisible());
    }

}

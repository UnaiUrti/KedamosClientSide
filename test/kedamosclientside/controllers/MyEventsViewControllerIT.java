package kedamosclientside.controllers;

import java.util.concurrent.TimeoutException;
import javafx.stage.Stage;
import kedamosclientside.KedamosApp;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import java.text.ParseException;
import java.util.Random;
import static org.testfx.matcher.base.NodeMatchers.*;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Adrian Franco
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MyEventsViewControllerIT extends ApplicationTest {

    private static String myEvent = "verify Create";

    /**
     * Starts application to be tested.
     *
     * @param stage Primary Stage object
     * @throws Exception If there is any error
     */
    @BeforeClass
    public static void setUpClass() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(KedamosApp.class);

    }

    /**
     * This method allows to see users' table view by interacting with login
     * view.
     */
    
    //Este test va a dar Error en caso de que el servidor este iniciado, ya que verifica
    //que la alerta que te sale cuando el servidor está apagado aparece
    //Entonces si el servidor esta apagado el test dara okey
    @Test
    public void testA_NavegateEvent() {
        clickOn("#txtUsername");
        write("alex");
        clickOn("#txtPassword");
        write("abcd*1234");
        clickOn("#btnSignIn");

        //Este verify debería de dar error siempre que el lado servidor este apagado o el BASE_URI no este bien definido
        verifyThat("Server down, try again later", isVisible());

    }

    @Test
    public void testB_VerifyButtons() {
        clickOn("Events");
        clickOn("#miViewEvents");
        verifyThat("#btnAddPlace", isDisabled());
        verifyThat("#btnAddPersonal", isDisabled());
        verifyThat("#btnModify", isDisabled());
        verifyThat("#btnDelete", isDisabled());
    }

    @Test
    public void testC_Create() {
        clickOn("#tfTitle");
        write(myEvent);

        clickOn("#taDescription");
        write("Prueba Create");

        clickOn("#dpDate");
        //Fuerzo al robot a abrir el picker
        press(KeyCode.F4).release(KeyCode.F4);
        //Hago que seleccione la siguiente semana
        press(KeyCode.DOWN).release(KeyCode.DOWN);
        press(KeyCode.ENTER).release(KeyCode.ENTER);

        clickOn("#cmbCategory");
        press(KeyCode.DOWN).release(KeyCode.DOWN);
        press(KeyCode.DOWN).release(KeyCode.DOWN);
        press(KeyCode.DOWN).release(KeyCode.DOWN);
        press(KeyCode.ENTER).release(KeyCode.ENTER);

        clickOn("#tfPrice");
        write("0");

        clickOn("#tfMinParticipants");
        write("2");

        clickOn("#tfMaxParticipants");
        write("5");

        clickOn("#btnCreate");
        verifyThat("An event has been created successfully", isVisible());

        //Para quitar la alerta
        press(KeyCode.ENTER).release(KeyCode.ENTER);
    }   
        @Ignore
        @Test
    public void testD_ModifyBadDate() {
        //Clickar en el evento de la tabla
        clickOn(myEvent);
        verifyThat("#btnCreate", isDisabled());
        verifyThat("#btnModify", isEnabled());
        clickOn("#tfTitle");
        press(KeyCode.CONTROL);
        press(KeyCode.A).release(KeyCode.CONTROL).release(KeyCode.A);
        eraseText(1);

        myEvent = "verify modify";
        write(myEvent);
        clickOn("#taDescription");
        press(KeyCode.CONTROL);
        press(KeyCode.A).release(KeyCode.CONTROL).release(KeyCode.A);
        eraseText(1);
        write("Modify con fecha incorrecta");

        clickOn("#dpDate");
        //Fuerzo al robot a abrir el picker
        press(KeyCode.F4).release(KeyCode.F4);
        //Hago que seleccione la siguiente semana
        press(KeyCode.UP).release(KeyCode.UP);
        press(KeyCode.UP).release(KeyCode.UP);
        press(KeyCode.ENTER).release(KeyCode.ENTER);

        clickOn("#cmbCategory");
        press(KeyCode.DOWN).release(KeyCode.DOWN);
        press(KeyCode.DOWN).release(KeyCode.DOWN);
        press(KeyCode.DOWN).release(KeyCode.DOWN);
        press(KeyCode.DOWN).release(KeyCode.DOWN);
        press(KeyCode.DOWN).release(KeyCode.DOWN);
        press(KeyCode.ENTER).release(KeyCode.ENTER);

        clickOn("#tfPrice");
        press(KeyCode.CONTROL);
        press(KeyCode.A).release(KeyCode.CONTROL).release(KeyCode.A);
        eraseText(1);
        write("10");

        clickOn("#tfMaxParticipants");
        press(KeyCode.CONTROL);
        press(KeyCode.A).release(KeyCode.CONTROL).release(KeyCode.A);
        eraseText(1);
        write("10");

        clickOn("#btnModify");
        verifyThat("Fecha incorrecta", isVisible());
        clickOn("Aceptar");
        //Para quitar la alerta
        press(KeyCode.ENTER).release(KeyCode.ENTER);
    }


    @Test
    public void testE_Modify() {
        //Clickar en el evento de la tabla
        clickOn(myEvent);
        verifyThat("#btnCreate", isDisabled());
        verifyThat("#btnModify", isEnabled());
        clickOn("#tfTitle");
        press(KeyCode.CONTROL);
        press(KeyCode.A).release(KeyCode.CONTROL).release(KeyCode.A);
        eraseText(1);

        myEvent = "verify modify";
        write(myEvent);
        clickOn("#taDescription");
        press(KeyCode.CONTROL);
        press(KeyCode.A).release(KeyCode.CONTROL).release(KeyCode.A);
        eraseText(1);
        write("Pruebaaaaaa Modify");

        clickOn("#dpDate");
        //Fuerzo al robot a abrir el picker
        press(KeyCode.F4).release(KeyCode.F4);
        //Hago que seleccione la siguiente semana
        press(KeyCode.DOWN).release(KeyCode.DOWN);
        press(KeyCode.DOWN).release(KeyCode.DOWN);
        press(KeyCode.DOWN).release(KeyCode.DOWN);
        press(KeyCode.ENTER).release(KeyCode.ENTER);

        clickOn("#cmbCategory");
        press(KeyCode.DOWN).release(KeyCode.DOWN);
        press(KeyCode.DOWN).release(KeyCode.DOWN);
        press(KeyCode.DOWN).release(KeyCode.DOWN);
        press(KeyCode.DOWN).release(KeyCode.DOWN);
        press(KeyCode.DOWN).release(KeyCode.DOWN);
        press(KeyCode.ENTER).release(KeyCode.ENTER);

        clickOn("#tfPrice");
        press(KeyCode.CONTROL);
        press(KeyCode.A).release(KeyCode.CONTROL).release(KeyCode.A);
        eraseText(1);
        write("10");

        clickOn("#tfMaxParticipants");
        press(KeyCode.CONTROL);
        press(KeyCode.A).release(KeyCode.CONTROL).release(KeyCode.A);
        eraseText(1);
        write("10");

        clickOn("#btnModify");
        verifyThat("An event has been modify successfully", isVisible());

        //Para quitar la alerta
        press(KeyCode.ENTER).release(KeyCode.ENTER);
    }

    @Test
    public void testF_Delete() {
        clickOn(myEvent);
        clickOn("#btnDelete");
        verifyThat("Seguro que quieres eliminar el Evento?", isVisible());
        press(KeyCode.ENTER).release(KeyCode.ENTER);
    }
}

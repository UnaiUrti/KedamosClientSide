package kedamosclientside;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javax.ws.rs.core.GenericType;
import kedamosclientside.controllers.VPersonalResourceController;
import kedamosclientside.entities.Event;
import kedamosclientside.restful.EventClientRest;


/**
 * Clase principal la cual va a abrir la primera ventana SignIn
 * @author Unai Urtiaga
 */
public class Client extends Application{

    /**
     * Metodo main que va a ejecutar el metodo start
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Metodo start para abrir la primera ventana de la aplicacion
     * @param primaryStage Parametro de tipo Stage el cual se va a utilizar para
     * darle un nuevo stage al controlador
     * @throws Exception Un controlador para la excepcion del load()
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
           /*  <event> 
            <actualParticipants>2</actualParticipants> 
            <category>OCIO</category> 
            <date>2022-01-28T09:11:17+01:00</date> 
            <description>A</description> 
            <event_id>1</event_id> 
            <maxParticipants>9</maxParticipants> 
            <minParticipants>1</minParticipants> 
            <price>1.0</price> 
            <title>A</title> 
        </event> 
        
        */
           EventClientRest res=new EventClientRest();
        Event ev;
       /* 
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS:XX");
        Date date = sdf.parse("0001-01-01T00:00:00+01:00");
        ev.setActualParticipants(2L);
        ev.setCategory(Category.OCIO);
        ev.setDate(date);
        ev.setDescription("A");
        ev.setMaxParticipants(9L);
        ev.setMinParticipants(1L);
        ev.setPrice(1.0f);
        ev.setTitle("A");
        ev.setEvent_id(1L);*/
      Long id=Long.parseLong("1");
       ev = res.find(new GenericType<Event>() {},19L);
       
        
        
      
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/kedamosclientside/views/VPersonalResource.fxml"));
        
        Parent root = loader.load();
        
        VPersonalResourceController controller = ((VPersonalResourceController)loader.getController());
        controller.setStage(primaryStage);
        controller.initStage(root,ev);
        
    }
}
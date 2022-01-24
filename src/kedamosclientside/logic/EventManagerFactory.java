/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kedamosclientside.logic;

/**
 *
 * @author 2dam
 */

public class EventManagerFactory {

    public static EventManagerInterface getEventManagerImplementation() {

        EventManagerInterface emi = new EventManagerImplementation();
        return emi;

    }

}

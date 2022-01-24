/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kedamosclientside.logic;

/**
 *
 * @author Steven Arce
 */
public class ClientFactory {

    public static ClientInterface getClientImplementation() {
        ClientInterface ci = new ClientImplementation();
        return ci;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kedamosclientside.logic;

/**
 *Esta clase es la factoria de los personal resources
 * @author Irkus de la Fuente
 */
public class PersonalResourceManager {
    public static PersonalResourceInterface getImplementation(){
        PersonalResourceInterface per= new PersonalResourceImplementation();
        return per;
    }
}

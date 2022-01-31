/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kedamosclientside.logic;

import java.net.ConnectException;
import java.util.Collection;
import java.util.Set;
import javax.ws.rs.ClientErrorException;
import kedamosclientside.entities.Place;

/**
 *
 * @author UnaiUrtiaga
 */
public interface PlaceInterface {

    public Collection<Place> getAllPlaces() throws ConnectException;

    public void createPlace(Place place) throws ConnectException;

    public void updatePlace(Place place) throws ConnectException;

    public void deletePlace(Place place) throws ConnectException;

    public void deletePlaceAlternative(Place place) throws ConnectException;

    public Place getPlaceByAddress(Place place) throws ConnectException;

}

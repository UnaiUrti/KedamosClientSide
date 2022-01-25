/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kedamosclientside.logic;

import java.util.Collection;
import java.util.Set;
import javax.ws.rs.ClientErrorException;
import kedamosclientside.entities.Place;

/**
 *
 * @author UnaiUrtiaga
 */
public interface PlaceInterface {
    
    public Collection<Place> getAllPlaces();
    
    public void createPlace(Place place);
    
    public void updatePlace(Place place);
    
    public void deletePlace(Place place);
    
    public void deletePlaceAlternative(Place place);
    
    public Place getPlaceByAddress(Place place);
    
}

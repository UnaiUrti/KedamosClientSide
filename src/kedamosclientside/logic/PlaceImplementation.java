/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kedamosclientside.logic;

import java.util.Collection;
import java.util.List;
import javax.ws.rs.core.GenericType;
import kedamosclientside.entities.Place;
import kedamosclientside.restful.PlaceClientREST;

/**
 *
 * @author UnaiUrtiaga
 */
public class PlaceImplementation implements PlaceInterface {
    
    private PlaceClientREST webPlace;

    public PlaceImplementation() {
        webPlace = new PlaceClientREST();
    }
    
    
    @Override
    public Collection<Place> getAllPlaces(){
        
        Collection<Place> places = null;
        
        try{
            places = webPlace.findAll(new GenericType<Collection<Place>>(){});
            
        }catch(Exception e){
            
        }
        
        return places;
    }
    
    
    @Override
    public void createPlace(Place place) {
        
        webPlace.create(place);
        
    }
    
    
    @Override
    public void updatePlace(Place place) {

        webPlace.edit(place, place.getId().toString());

    }
    
    @Override
    public void deletePlace(Place place){
        
        webPlace.deletePlaceByAddress(place.getAddress());
        
    }
    
    @Override
    public void deletePlaceAlternative(Place place){
        
        webPlace.remove(place.getId().toString());
        
    }

    @Override
    public Place getPlaceByAddress(Place place) {
        
        Place tempPlace = null;
        
        tempPlace = webPlace.getPlaceByAddress(Place.class, place.getAddress());    
        
        return tempPlace;
    }
    
}

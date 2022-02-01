/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kedamosclientside.logic;

import java.net.ConnectException;
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
    public Collection<Place> getAllPlaces() throws ConnectException {

        Collection<Place> places = null;

        try {
            places = webPlace.findAll(new GenericType<Collection<Place>>() {
            });

        } catch (Exception e) {
            throw new ConnectException("No se ha podido conectar con la base de datos");
        }

        return places;
    }

    @Override
    public void createPlace(Place place) throws ConnectException {
        try {
            webPlace.create(place);
        } catch (Exception e) {
            throw new ConnectException("No se ha podido conectar con la base de datos");
        }
    }

    @Override
    public void updatePlace(Place place) throws ConnectException {
        try {
            webPlace.edit(place, place.getId().toString());
        } catch (Exception e) {
            throw new ConnectException("There was an error connecting with the server. Try again later.");
        }
    }

    @Override
    public void deletePlace(Place place) throws ConnectException {
        try {
            webPlace.deletePlaceByAddress(place.getAddress());
        } catch (Exception e) {
            throw new ConnectException("There was an error connecting with the server. Try again later.");
        }
    }

    @Override
    public void deletePlaceAlternative(Place place) throws ConnectException {
        try {
            webPlace.remove(place.getId().toString());
        } catch (Exception e) {
            throw new ConnectException("There was an error connecting with the server. Try again later.");
        }
    }

    @Override
    public Place getPlaceByAddress(Place place) throws ConnectException {

        Place tempPlace = null;
        try {
            tempPlace = webPlace.getPlaceByAddress(Place.class, place.getAddress());
        } catch (Exception e) {
            throw new ConnectException("There was an error connecting with the server. Try again later.");
        }
        return tempPlace;
    }

}

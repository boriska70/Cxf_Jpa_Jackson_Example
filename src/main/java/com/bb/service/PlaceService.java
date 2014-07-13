package com.bb.service;

import com.bb.dal.entity.Place;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: belozovs
 * Date: 7/2/14
 * Time: 5:00 PM
 * To change this template use File | Settings | File Templates.
 */
public interface PlaceService {

    List<Place> getAllPlaces();
    List<Place> findPlacesByPattern(String name);
    Place getPlaceByName(String name);
    Place getPlaceByAlias(String alias);

    Place addPlace(Place place);
    Place updatePlace(Place place);
    Place removePlace(String name);

}

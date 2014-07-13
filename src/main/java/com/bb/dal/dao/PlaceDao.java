package com.bb.dal.dao;

import com.bb.dal.entity.Place;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: belozovs
 * Date: 7/2/14
 * Time: 10:36 AM
 * To change this template use File | Settings | File Templates.
 */
public interface PlaceDao {

    void addPlace(Place place);
    Place updatePlace(Place place) throws PlaceDatabaseException;
    void removePlace(String name);

    Place getPlaceByName(String name);
    Place getPlaceByAlias(String alias);
    List<Place> getAllPlaces();

    List<Place> getPlacesByPattern(String strName);
}

package com.bb.service;

import com.bb.dal.dao.AddressDao;
import com.bb.dal.dao.PlaceDao;
import com.bb.dal.dao.PlaceDatabaseException;
import com.bb.dal.entity.Address;
import com.bb.dal.entity.Place;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: belozovs
 * Date: 7/2/14
 * Description
 */

@Service
public class PlaceServiceImpl implements PlaceService {

    Logger logger = LoggerFactory.getLogger(PlaceServiceImpl.class);

    @Autowired
    PlaceDao placeDao;
    @Autowired
    AddressDao addressDao;

    @Transactional(readOnly = true)
    @Override
    public List<Place> getAllPlaces() {
        List<Place> places = placeDao.getAllPlaces();
        logger.info("All places found: {}", places.size());
        return places;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Place> findPlacesByPattern(String name) {
        List<Place> places = placeDao.getPlacesByPattern(name);
        logger.info("Found {} places with {} in their name", places.size(), name);
        return places;
    }

    @Transactional(readOnly = true)
    @Override
    public Place getPlaceByName(String name) {
        Place place = placeDao.getPlaceByName(name);
        if(place!=null){
            logger.info("Place {} was found", name);
        } else {
            logger.warn("Cannot find place {}", name);
        }

        return place;
    }

    @Override
    public Place getPlaceByAlias(String alias) {
        Place place = placeDao.getPlaceByAlias(alias);
        if(place!=null){
            logger.info("Place with alias {} was found. Its name is {}", alias, place.getName());
        } else {
            logger.warn("Cannot find place with alias {}", alias);
        }
        return place;
    }

    @Transactional
    @Override
    public Place addPlace(Place place) {
        try {
            validatePlace(place);
            place.setAlias(generatePlaceAlias());
            placeDao.addPlace(place);

            Place myPlace = placeDao.getPlaceByName(place.getName());

            List<Address> addressList = place.getAddresses();
            if(addressList!=null && !addressList.isEmpty()){
                for(Address address: addressList){
                    address.setPlace(myPlace);
                    addressDao.addAddress(address);
                }
            }

            return place;
        } catch (PlaceServiceException e) {
            logger.error(e.getLocalizedMessage());
            return null;
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(),e);
            return null;
        }
    }

    @Transactional
    @Override
    public Place updatePlace(Place place) {
        try{
            validatePlace(place);
            List<Address> newAddresses = place.getAddresses();

            Place oldPlace = placeDao.getPlaceByAlias(place.getAlias());
            if(oldPlace!=null){
                newAddresses = place.getAddresses();
                if(newAddresses!=null && !newAddresses.isEmpty()) {
                    for(Address address : newAddresses){
                        address.setPlace(oldPlace);
                        if(addressDao.getAddressByAlias(address.getAlias(),oldPlace.getId()) == null){
                            addressDao.addAddress(address);
                        } else {
                            addressDao.updateAddressByAlias(address);
                        }
                    }
                }
            }
            place.setAddresses(addressDao.getAddressesForPlace(oldPlace.getId()));
            Place updatedPlace = placeDao.updatePlace(place);
            return updatedPlace;
        } catch (PlaceServiceException e){
            logger.error(e.getLocalizedMessage());
            return null;
        } catch (PlaceDatabaseException e) {
            logger.error(e.getLocalizedMessage());
            return null;
        } catch (Exception e){
            logger.error(e.getLocalizedMessage(),e);
            return null;
        }
    }

    @Transactional
    @Override
    public Place removePlace(String placeName) {
        Place place = placeDao.getPlaceByName(placeName);
        if(place == null){
            logger.warn("Nothing to delete - place {} not found in DB", placeName);
            return null;
        }
        try {
            placeDao.removePlace(placeName);
            return place;
        } catch (Exception e) {
            logger.error("Cannot delete place {}", place, e);
            return null;
        }
    }

    private void validatePlace(Place place) throws PlaceServiceException{
        if(StringUtils.isEmpty(place.getName())){
            throw new PlaceServiceException("Place must have non-empty name");
        }
    }

    private String generatePlaceAlias(){
        return "place_"+System.currentTimeMillis();
    }
}

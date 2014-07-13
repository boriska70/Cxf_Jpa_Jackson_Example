package com.bb.dal.dao;

import com.bb.dal.entity.Place;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * User: belozovs
 * Date: 7/2/14
 * Description
 */

@Repository
public class PlaceDaoImpl implements PlaceDao {

    @PersistenceContext(unitName = "myEntityManager")
    EntityManager em;

    @Override
    public void addPlace(Place place) {
        em.persist(place);
    }

    @Override
    public Place updatePlace(Place place) throws PlaceDatabaseException {
        Place place1 = getPlaceByName(place.getName());
        if (place1 == null) {
            throw new PlaceDatabaseException("Attempt to update non-existing place");
        }
        place1.setNotes(place.getNotes());
        place1.setAddresses(place.getAddresses());
        em.merge(place1);
        return place1;
    }

    @Override
    public void removePlace(String name) {
        Place place = getPlaceByName(name);
        if(place != null){
            em.remove(place);
        }
    }

    @Override
    public Place getPlaceByName(String aName) {
        Query query = em.createQuery("FROM Place WHERE name=:nameParam");
        query.setParameter("nameParam", aName);
        List<Place> places = query.getResultList();
        if (places != null && !places.isEmpty()) {
            return places.get(0);
        }
        else {
            return null;
        }
    }

    @Override
    public Place getPlaceByAlias(String alias) {
        Query query = em.createQuery("FROM Place WHERE alias =:alias");
        query.setParameter("alias", alias);
        List<Place> places = query.getResultList();
        if (places != null && !places.isEmpty()) {
            return places.get(0);
        }
        else {
            return null;
        }
    }

    @Override
    public List<Place> getAllPlaces() {
        Query query = em.createQuery("FROM Place");
        return query.getResultList();
    }

    @Override
    public List<Place> getPlacesByPattern(String strName) {
        Query query = em.createQuery("FROM Place where name like :nameParam");
        query.setParameter("nameParam", "%"+strName+"%");
        List<Place> places = query.getResultList();
        return places;
    }
}

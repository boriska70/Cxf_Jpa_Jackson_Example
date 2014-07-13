package com.bb.dal.dao;

import com.bb.dal.entity.Address;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * User: belozovs
 * Date: 7/7/14
 * Description
 */

@Repository
public class AddressDaoImpl implements AddressDao{

    @PersistenceContext(unitName = "myEntityManager")
    EntityManager em;

    @Override
    public void addAddress(Address address) {
        em.persist(address);
    }

    @Override
    public void removeAddress(Address address) {
        em.remove(address);
    }

    @Override
    public void updateAddressByAlias(Address address) {
        List<Address> addressList = getAddressesForPlace(address.getPlace().getId());
        for(Address address1 : addressList) {
            if(address1.getAlias().equals(address.getAlias())) {
                address1.setAddress(address.getAddress());
                address1.setMetro(address.getMetro());
                address1.setNotes(address.getNotes());
                em.merge(address1);
                break;
            }
        }
    }

    @Override
    public Address getAddressByAlias(String alias, int placeId) {
        Query query = em.createNamedQuery("Address.getAddressByAlias");
        query.setParameter("alias", alias);
        query.setParameter("pid", placeId);
        query.getResultList();

        List<Address> addressList = query.getResultList();
        if(addressList != null && !addressList.isEmpty()){
            return addressList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<Address> getAddressesForPlace(int placeId) {

        Query query = em.createNamedQuery("Address.getAddressesForPlace");
        query.setParameter("pid", placeId);
        List<Address> addressList = query.getResultList();

        return addressList;
    }


}

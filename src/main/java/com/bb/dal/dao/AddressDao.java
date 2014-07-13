package com.bb.dal.dao;

import com.bb.dal.entity.Address;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: belozovs
 * Date: 7/7/14
 * Time: 10:39 AM
 * To change this template use File | Settings | File Templates.
 */
public interface AddressDao {

    void addAddress(Address address);
    void removeAddress(Address address);
    void updateAddressByAlias(Address address);

    List<Address> getAddressesForPlace(int sightId);

    Address getAddressByAlias(String alias, int placeId);
}

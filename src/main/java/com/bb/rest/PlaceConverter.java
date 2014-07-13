package com.bb.rest;

import com.bb.dal.entity.Address;
import com.bb.dal.entity.Place;
import com.bb.rest.dto.AddressDTO;
import com.bb.rest.dto.PlaceDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * User: belozovs
 * Date: 7/5/14
 * Description
 */
@Component
public class PlaceConverter {


    public PlaceDTO setDtoFromEntity(Place place) {
        return setDtoFromEntity(place, true);
    }

    public PlaceDTO setDtoFromEntity(Place place, boolean nested) {
        PlaceDTO placeDTO = new PlaceDTO();
        placeDTO.setAlias(place.getAlias());
        placeDTO.setName(place.getName());
        placeDTO.setNotes(place.getNotes());

        if (nested) {

            List<Address> addressList = place.getAddresses();

            List<AddressDTO> addressDTOs = new ArrayList<AddressDTO>();
            if (addressList != null && !addressList.isEmpty()) {
                for (Address address : addressList) {
                    AddressDTO addressDTO = new AddressDTO();
                    addressDTO.setAlias(address.getAlias());
                    addressDTO.setAddress(address.getAddress());
                    addressDTO.setMetro(address.getMetro());
                    addressDTO.setNotes(address.getNotes());
                    addressDTOs.add(addressDTO);
                }
            }

            placeDTO.setAddresses(addressDTOs);
        }

        return placeDTO;
    }

    public Place setEntityFromDto(PlaceDTO placeDTO) {
        Place place = new Place();
        place.setName(placeDTO.getName());
        place.setNotes(placeDTO.getNotes());
        place.setAlias(placeDTO.getAlias());

        List<AddressDTO> addressDTOs = placeDTO.getAddresses();

        List<Address> addressList = new ArrayList<Address>();
        if (addressDTOs != null && !addressDTOs.isEmpty()) {
            for (AddressDTO addressDTO : addressDTOs) {
                Address address = new Address();
                address.setAlias(addressDTO.getAlias());
                address.setAddress(addressDTO.getAddress());
                address.setMetro(addressDTO.getMetro());
                address.setNotes(addressDTO.getNotes());
                addressList.add(address);
            }
        }

        place.setAddresses(addressList);

        return place;
    }

}

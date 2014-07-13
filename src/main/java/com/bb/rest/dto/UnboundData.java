package com.bb.rest.dto;

import com.bb.dal.entity.Address;
import com.bb.dal.entity.Place;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * User: belozovs
 * Date: 7/13/14
 * Description
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
//do not send null values (or see serializeWithMapper in SightseeingService, should use only 1 of them actually)
@XmlRootElement(name = "unboundData")
public class UnboundData {

    @XmlElement(name = "places") //otherwise  placeList shown as a name
    @JsonProperty("places")
    private List<Place> placeList;
    @XmlElement(name = "addresses") //otherwise  addressesList shown as a name
    @JsonProperty("addresses")
    private List<UnboundAddress> addressesList;

    public UnboundData(List<Place> placeList, List<Address> addressesList) {
        if (placeList != null && !placeList.isEmpty()) {
            for (Place place : placeList) {
                place.setAddresses(null);
            }
        }
        this.placeList = placeList;

        this.addressesList = new ArrayList<UnboundAddress>();
        if (addressesList != null && !addressesList.isEmpty()) {
            for (Address address : addressesList) {
                this.addressesList.add(new UnboundAddress(address));
            }
        }
    }

}

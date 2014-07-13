package com.bb.rest.dto;

import com.bb.dal.entity.Address;
import com.bb.dal.entity.Place;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * User: belozovs
 * Date: 7/13/14
 * Description
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class UnboundAddress {

    @JsonProperty("address_id")
    int id;
    @JsonProperty("alias")
    String alias;
    @JsonProperty("address")
    String address;
    @JsonProperty("metro_station")
    String metro;
    @JsonProperty("notes")
    String notes;
    @JsonProperty("place")
    String place;

    public int getId() {
        return id;
    }

    public String getAlias() {
        return alias;
    }

    public String getAddress() {
        return address;
    }

    public String getMetro() {
        return metro;
    }

    public String getNotes() {
        return notes;
    }

    public String getPlace() {
        return place;
    }

    public UnboundAddress(Address address) {

        this.id = address.getId();
        this.alias = address.getAlias();
        this.address = address.getAddress();
        this.metro = address.getMetro();
        this.notes = address.getNotes();
        //we don't need to keep the entire place but we want to keep a reference
        Place place = address.getPlace();
        StringBuffer sb = new StringBuffer(place.getName()).append(" (id: "+place.getId()+")");
        this.place = sb.toString();
    }
}

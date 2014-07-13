package com.bb.rest.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * User: belozovs
 * Date: 7/5/14
 * Description
 */
@XmlRootElement(name = "place")
public class PlaceDTO {

    @XmlElement(name = "alias")
    private String alias;
    @XmlElement(name = "name")
    private String name;
    @XmlElement(name = "notes")
    private String notes;

    @XmlElementWrapper(name = "addresses", nillable = true)
    @XmlElement(name = "address")
    List<AddressDTO> addresses;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<AddressDTO> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressDTO> addresses) {
        this.addresses = addresses;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}

package com.bb.rest.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * User: belozovs
 * Date: 7/7/14
 * Description
 */

@XmlRootElement(name = "address")
public class AddressDTO {

    @XmlElement(name = "alias")
    private String alias;
    @XmlElement(name = "address")
    private String address;
    @XmlElement(name = "metro")
    private String metro;
    @XmlElement(name = "notes")
    private String notes;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMetro() {
        return metro;
    }

    public void setMetro(String metro) {
        this.metro = metro;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}

package com.bb.dal.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * User: belozovs
 * Date: 7/7/14
 * Description
 */
@Entity
@Table(name = "addresses", uniqueConstraints = @UniqueConstraint(columnNames = {"sight_id", "alias"}))
@NamedQueries(value = {
        @NamedQuery(name = "Address.getAddressByAlias", query = "SELECT adr FROM Address adr join adr.place pl WHERE pl.id = :pid and adr.alias = :alias"),
        @NamedQuery(name = "Address.getAddressesForPlace", query = "SELECT adr FROM Address adr join adr.place pl WHERE pl.id = :pid")
})
public class Address {

    public Address(){
    }

    public Address(Address oldAddress){
        this.setAlias(oldAddress.getAlias());
        this.setAddress(oldAddress.getAddress());
        this.setMetro(oldAddress.getMetro());
        this.setNotes(oldAddress.getNotes());
        this.setPlace(oldAddress.getPlace());
    }


    @Id
    @GenericGenerator(name = "addressGen", strategy = "increment")
    @GeneratedValue(generator = "addressGen")
    @Column(name = "idaddress", nullable = false)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sight_id", nullable = false)
    private Place place;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "metro", nullable = true)
    private String metro;

    @Column(name = "notes", nullable = true)
    private String notes;

    @Column(name = "alias", nullable = false)
    private String alias;

    public int getId() {
        return id;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
        if(place!=null && !place.getAddresses().contains(this)){
            place.addAddress(this);
        }
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

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }


}

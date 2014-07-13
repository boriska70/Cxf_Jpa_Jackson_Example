package com.bb.dal.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * User: belozovs
 * Date: 7/2/14
 * Description
 */

@Entity
@Table(name = "place", uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
public class Place {

    @Id
    @GenericGenerator(name = "place_id_generator", strategy = "increment")
    @GeneratedValue(generator = "place_id_generator")
    @Column(name = "idsightseeing", nullable = false)
    private int Id;
    @Column(name = "alias", nullable = false)
    private String alias;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "notes", nullable = true)
    private String notes;

    @OneToMany(mappedBy = "place", fetch = FetchType.EAGER)
    private List<Address> addresses = new ArrayList<Address>();

    public int getId() {
        return Id;
    }

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

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public void addAddress(Address address){
        if(!this.addresses.contains(address)){
            addresses.add(address);
        }
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Place place = (Place) o;

        if (!name.equals(place.name)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}

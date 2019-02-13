package com.centralvet.core.request;

import com.centralvet.core.entities.Pet;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

public class CustomerRequest {

    String name;
    String address;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<Pet> pets;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }
}

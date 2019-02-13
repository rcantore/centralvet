package com.centralvet.core.response;

import com.centralvet.core.entities.Clinic;
import com.centralvet.core.entities.Customer;
import com.centralvet.core.entities.Pet;

import java.util.ArrayList;
import java.util.List;

public class ClinicServiceResponse {

    String status;
    String message;

    List<Clinic> clinics;
    List<Customer> customers;
    List<Pet> pets;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Clinic> getClinics() {
        if (clinics == null) {
            clinics = new ArrayList<>();
        }
        return clinics;
    }

    public void setClinics(List<Clinic> clinics) {
        this.clinics = clinics;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }
}

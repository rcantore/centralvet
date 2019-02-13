package com.centralvet.core.response;

import com.centralvet.core.entities.Clinic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClinicServiceResponse {

    String status;
    String message;

    List<Clinic> clinics;

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
}

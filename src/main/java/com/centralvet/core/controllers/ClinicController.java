package com.centralvet.core.controllers;

import com.centralvet.core.entities.Clinic;
import com.centralvet.core.entities.Customer;
import com.centralvet.core.entities.repositories.ClinicRepository;
import com.centralvet.core.entities.repositories.CustomerRepository;
import com.centralvet.core.request.ClinicRequest;
import com.centralvet.core.request.CustomerRequest;
import com.centralvet.core.response.ClinicServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Service
@Path("/api/clinics")
public class ClinicController {

    @Autowired
    ClinicRepository clinicRepository;

    @Autowired
    CustomerRepository customerRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ClinicServiceResponse getClinics() {
        ClinicServiceResponse clinicServiceResponse = new ClinicServiceResponse();
        clinicServiceResponse.setMessage("successfully");
        clinicServiceResponse.setStatus("OK");

        List<Clinic> clinics = (List<Clinic>) clinicRepository.findAll();

        //FIXME use getter
        clinicServiceResponse.setClinics(clinics);

        return clinicServiceResponse;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public ClinicServiceResponse postClinic(@RequestBody ClinicRequest body) {
        ClinicServiceResponse clinicServiceResponse = new ClinicServiceResponse();
        clinicServiceResponse.setMessage("successfully");
        clinicServiceResponse.setStatus("OK");

        Clinic clinic = new Clinic();
        clinic.setAddress(body.getAddress());
        clinic.setName(body.getName());
        Clinic savedClinic = clinicRepository.save(clinic);
        clinicServiceResponse.getClinics().add(savedClinic);

        return clinicServiceResponse;
    }

    @POST
    @Path("{id}/customers")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postCostumerToClinic(CustomerRequest body, @PathParam("id") Long id) {
        ClinicServiceResponse clinicServiceResponse = new ClinicServiceResponse();
        clinicServiceResponse.setMessage("successfully");
        clinicServiceResponse.setStatus("OK");

        Optional<Clinic> clinicOptional = clinicRepository.findById(id);
        if(!clinicOptional.isPresent()) {
            clinicServiceResponse.setMessage("error");
            clinicServiceResponse.setStatus("ERR");

            return Response.status(HttpStatus.NOT_FOUND.value()).entity(clinicServiceResponse).build();
        }

        Clinic clinic = clinicOptional.get();

        Customer customer = new Customer();
        customer.setAddress(body.getAddress());
        customer.setName(body.getName());
        customer.setClinic(clinic);

        Customer savedCustomer = customerRepository.save(customer);

        clinic.getCustomers().add(savedCustomer);
        clinicRepository.save(clinic);

        return Response.status(HttpStatus.OK.value()).entity(clinicServiceResponse).build();
    }

}

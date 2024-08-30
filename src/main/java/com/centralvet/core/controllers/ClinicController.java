package com.centralvet.core.controllers;

import com.centralvet.core.entities.Clinic;
import com.centralvet.core.entities.Customer;
import com.centralvet.core.entities.repositories.ClinicRepository;
import com.centralvet.core.entities.repositories.CustomerRepository;
import com.centralvet.core.request.ClinicRequest;
import com.centralvet.core.request.CustomerRequest;
import com.centralvet.core.response.ClinicResponse;
import com.centralvet.core.response.ClinicsResponse;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;


@OpenAPIDefinition
@RestController
@RequestMapping("/api/clinics")
public class ClinicController {
    private final ClinicRepository clinicRepository;

    private final CustomerRepository customerRepository;

    public ClinicController(ClinicRepository clinicRepository, CustomerRepository customerRepository) {
        this.clinicRepository = clinicRepository;
        this.customerRepository = customerRepository;
    }

    @Operation(summary = "Get all clinics")
    @GetMapping
    public ClinicsResponse getClinics(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size) {

        ClinicsResponse clinicsResponse = new ClinicsResponse();
        clinicsResponse.setMessage("successfully");
        clinicsResponse.setStatus("OK");


        Pageable pageable = PageRequest.of(page, size);
        Page<Clinic> clinics = clinicRepository.findAll(pageable);

        clinicsResponse.setClinics(clinics.getContent());

        return clinicsResponse;
    }

    @Operation(summary = "Create a new clinic")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @PostMapping
    public ClinicsResponse postClinic(@RequestBody ClinicRequest body) {
        ClinicsResponse clinicsResponse = new ClinicsResponse();
        clinicsResponse.setMessage("successfully");
        clinicsResponse.setStatus("OK");

        Clinic clinic = new Clinic();
        clinic.setAddress(body.getAddress());
        clinic.setName(body.getName());
        Clinic savedClinic = clinicRepository.save(clinic);
        clinicsResponse.getClinics().add(savedClinic);

        return clinicsResponse;
    }

    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @PostMapping("/{id}/customers")
    public Response postCostumerToClinic(CustomerRequest body, @PathVariable Long id) {
        ClinicsResponse clinicsResponse = new ClinicsResponse();
        clinicsResponse.setMessage("successfully");
        clinicsResponse.setStatus("OK");

        Optional<Clinic> clinicOptional = clinicRepository.findById(id);
        if(!clinicOptional.isPresent()) {
            clinicsResponse.setMessage("error");
            clinicsResponse.setStatus("ERR");

            return Response.status(HttpStatus.NOT_FOUND.value()).entity(clinicsResponse).build();
        }

        Clinic clinic = clinicOptional.get();

        Customer customer = new Customer();
        customer.setAddress(body.getAddress());
        customer.setName(body.getName());
        customer.setClinic(clinic);

        Customer savedCustomer = customerRepository.save(customer);

        clinic.getCustomers().add(savedCustomer);
        clinicRepository.save(clinic);

        return Response.status(HttpStatus.OK.value()).entity(clinicsResponse).build();
    }


    @Operation(summary = "Get customers by clinic")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @GetMapping("/{id}/customers")
    public Response getCustomersForClinic(@PathVariable("id") Long id) {
        ClinicResponse clinicResponse = new ClinicResponse();
        clinicResponse.setMessage("successfully");
        clinicResponse.setStatus("OK");

        Optional<Clinic> clinicOptional = clinicRepository.findById(id);
        if(clinicOptional.isEmpty()) {
            clinicResponse.setMessage("error");
            clinicResponse.setStatus("ERR");

            return Response.status(HttpStatus.NOT_FOUND.value()).entity(clinicResponse).build();
        }

        List<Customer> allByClinic = customerRepository.findAllByClinic(clinicOptional.get());

        clinicResponse.setCustomers(allByClinic);

        return Response.status(HttpStatus.OK.value()).entity(clinicResponse).build();

    }
}

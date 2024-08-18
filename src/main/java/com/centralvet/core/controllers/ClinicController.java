package com.centralvet.core.controllers;

import com.centralvet.core.entities.Clinic;
import com.centralvet.core.entities.Customer;
import com.centralvet.core.entities.repositories.ClinicRepository;
import com.centralvet.core.entities.repositories.CustomerRepository;
import com.centralvet.core.request.ClinicRequest;
import com.centralvet.core.request.CustomerRequest;
import com.centralvet.core.response.ClinicServiceResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @Autowired
    CustomerRepository customerRepository;

    public ClinicController(ClinicRepository clinicRepository) {
        this.clinicRepository = clinicRepository;
    }

    @Operation(summary = "Get all clinics")
    @GetMapping
    public ClinicServiceResponse getClinics(
            @QueryParam("page") @DefaultValue("0") Integer page,
            @QueryParam("size") @DefaultValue("10") Integer size,
            @QueryParam("name") String name) {

        ClinicServiceResponse clinicServiceResponse = new ClinicServiceResponse();
        clinicServiceResponse.setMessage("successfully");
        clinicServiceResponse.setStatus("OK");

        final Clinic exampleClinic = new Clinic();
        exampleClinic.setName(name);
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Clinic> example = Example.of(exampleClinic, matcher);

        Pageable pageable = PageRequest.of(page, size);
        Page<Clinic> clinics = clinicRepository.findAll(example, pageable);

        //FIXME use getter
        clinicServiceResponse.setClinics(clinics.getContent());

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


    @GET
    @Path("{id}/customers")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getCustomersForClinic(@PathParam("id") Long id) {
        ClinicServiceResponse clinicServiceResponse = new ClinicServiceResponse();
        clinicServiceResponse.setMessage("successfully");
        clinicServiceResponse.setStatus("OK");

        Optional<Clinic> clinicOptional = clinicRepository.findById(id);
        if(!clinicOptional.isPresent()) {
            clinicServiceResponse.setMessage("error");
            clinicServiceResponse.setStatus("ERR");

            return Response.status(HttpStatus.NOT_FOUND.value()).entity(clinicServiceResponse).build();
        }

        List<Customer> allByClinic = customerRepository.findAllByClinic(clinicOptional.get());

        clinicServiceResponse.setCustomers(allByClinic);

        return Response.status(HttpStatus.OK.value()).entity(clinicServiceResponse).build();

    }
}

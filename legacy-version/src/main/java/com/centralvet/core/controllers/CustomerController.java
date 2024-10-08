package com.centralvet.core.controllers;

import com.centralvet.core.entities.Customer;
import com.centralvet.core.entities.Pet;
import com.centralvet.core.entities.repositories.ClinicRepository;
import com.centralvet.core.entities.repositories.CustomerRepository;
import com.centralvet.core.entities.repositories.PetRepository;
import com.centralvet.core.request.PetRequest;
import com.centralvet.core.response.ClinicsResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@OpenAPIDefinition
@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    private final ClinicRepository clinicRepository;

    private final CustomerRepository customerRepository;

    private final PetRepository petRepository;

    public CustomerController(ClinicRepository clinicRepository, CustomerRepository customerRepository, PetRepository petRepository) {
        this.clinicRepository = clinicRepository;
        this.customerRepository = customerRepository;
        this.petRepository = petRepository;
    }

    @Operation(summary = "Get all customers")
    @GetMapping
    public ClinicsResponse getCustomers(
        @QueryParam("page") @DefaultValue("0") Integer page,
        @QueryParam("size") @DefaultValue("10") Integer size,
        @QueryParam("name") String name) {
        //fixme use a different response object

        ClinicsResponse clinicsResponse = new ClinicsResponse();
        clinicsResponse.setMessage("successfully");
        clinicsResponse.setStatus("OK");

        final Customer exampleCustomer = new Customer();
        exampleCustomer.setName(name);
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(exampleCustomer, matcher);

        Pageable pageable = PageRequest.of(page, size);

        Page<Customer> customers = customerRepository.findAll(example, pageable);

        //FIXME use getter
        //clinicsResponse.setCustomers(customers.getContent());

        return clinicsResponse;
    }

    @POST
    @Path("{id}/pets")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation("Add a pet to a customer")
    public Response postPetToCustomer(PetRequest body, @PathParam("id") Long id) {
        ClinicsResponse clinicsResponse = new ClinicsResponse();
        clinicsResponse.setMessage("successfully");
        clinicsResponse.setStatus("OK");

        Optional<Customer> customerOptional = customerRepository.findById(id);
        if(!customerOptional.isPresent()) {
            clinicsResponse.setMessage("error");
            clinicsResponse.setStatus("ERR");

            return Response.status(HttpStatus.NOT_FOUND.value()).entity(clinicsResponse).build();
        }

        Customer customer = customerOptional.get();

        Pet pet = new Pet();
        pet.setName(body.getName());
        pet.setBreed(body.getBreed());
        pet.setCustomer(customer);

        Pet savedPet = petRepository.save(pet);

        customer.getPets().add(savedPet);
        customerRepository.save(customer);

        return Response.status(HttpStatus.OK.value()).entity(clinicsResponse).build();
    }

    @GET
    @Path("{id}/pets")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation("Get pets by customer")
    public Response getPetsByCustomer(@PathParam("id") Long id,
                                      @QueryParam("page") @DefaultValue("0") Integer page,
                                      @QueryParam("size") @DefaultValue("10") Integer size,
                                      @QueryParam("name") String name) {
        //FIXME use a different response object
        ClinicsResponse clinicsResponse = new ClinicsResponse();
        clinicsResponse.setMessage("successfully");
        clinicsResponse.setStatus("OK");

        Optional<Customer> customerOptional = customerRepository.findById(id);
        if(!customerOptional.isPresent()) {
            clinicsResponse.setMessage("error");
            clinicsResponse.setStatus("ERR");

            return Response.status(HttpStatus.NOT_FOUND.value()).entity(clinicsResponse).build();
        }

        Customer customer = customerOptional.get();

        final Pet examplePet = new Pet();
        examplePet.setName(name);
        examplePet.setCustomer(customer);
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(examplePet, matcher);

        Pageable pageable = PageRequest.of(page, size);

        Page<Pet> allByCustomer = petRepository.findAll(example, pageable);

        //clinicsResponse.setPets(allByCustomer.getContent());

        return Response.status(HttpStatus.OK.value()).entity(clinicsResponse).build();
    }
}

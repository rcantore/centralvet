package com.centralvet.core.controllers;

import com.centralvet.core.entities.Clinic;
import com.centralvet.core.entities.Customer;
import com.centralvet.core.entities.Pet;
import com.centralvet.core.entities.repositories.ClinicRepository;
import com.centralvet.core.entities.repositories.CustomerRepository;
import com.centralvet.core.entities.repositories.PetRepository;
import com.centralvet.core.request.PetRequest;
import com.centralvet.core.response.ClinicServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Service
@Path("/api/customers")
public class CustomerController {

    @Autowired
    ClinicRepository clinicRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    PetRepository petRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ClinicServiceResponse getCustomers(
        @QueryParam("page") @DefaultValue("0") Integer page,
        @QueryParam("size") @DefaultValue("10") Integer size,
        @QueryParam("name") String name) {

        ClinicServiceResponse clinicServiceResponse = new ClinicServiceResponse();
        clinicServiceResponse.setMessage("successfully");
        clinicServiceResponse.setStatus("OK");

        final Customer exampleCustomer = new Customer();
        exampleCustomer.setName(name);
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(exampleCustomer, matcher);

        Pageable pageable = PageRequest.of(page, size);

        Page<Customer> customers = customerRepository.findAll(example, pageable);

        //FIXME use getter
        clinicServiceResponse.setCustomers(customers.getContent());

        return clinicServiceResponse;
    }

    @POST
    @Path("{id}/pets")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postPetToCustomer(PetRequest body, @PathParam("id") Long id) {
        ClinicServiceResponse clinicServiceResponse = new ClinicServiceResponse();
        clinicServiceResponse.setMessage("successfully");
        clinicServiceResponse.setStatus("OK");

        Optional<Customer> customerOptional = customerRepository.findById(id);
        if(!customerOptional.isPresent()) {
            clinicServiceResponse.setMessage("error");
            clinicServiceResponse.setStatus("ERR");

            return Response.status(HttpStatus.NOT_FOUND.value()).entity(clinicServiceResponse).build();
        }

        Customer customer = customerOptional.get();

        Pet pet = new Pet();
        pet.setName(body.getName());
        pet.setBreed(body.getBreed());
        pet.setCustomer(customer);

        Pet savedPet = petRepository.save(pet);

        customer.getPets().add(savedPet);
        customerRepository.save(customer);

        return Response.status(HttpStatus.OK.value()).entity(clinicServiceResponse).build();
    }

    @GET
    @Path("{id}/pets")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getPetsByCustomer(@PathParam("id") Long id,
                                      @QueryParam("page") @DefaultValue("0") Integer page,
                                      @QueryParam("size") @DefaultValue("10") Integer size,
                                      @QueryParam("name") String name) {
        ClinicServiceResponse clinicServiceResponse = new ClinicServiceResponse();
        clinicServiceResponse.setMessage("successfully");
        clinicServiceResponse.setStatus("OK");

        Optional<Customer> customerOptional = customerRepository.findById(id);
        if(!customerOptional.isPresent()) {
            clinicServiceResponse.setMessage("error");
            clinicServiceResponse.setStatus("ERR");

            return Response.status(HttpStatus.NOT_FOUND.value()).entity(clinicServiceResponse).build();
        }

        Customer customer = customerOptional.get();

        final Pet examplePet = new Pet();
        examplePet.setName(name);
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(examplePet, matcher);

        Pageable pageable = PageRequest.of(page, size);

        Page<Pet> allByCustomer = petRepository.findAllByCustomer(customer, example, pageable);

        clinicServiceResponse.setPets(allByCustomer.getContent());

        return Response.status(HttpStatus.OK.value()).entity(clinicServiceResponse).build();
    }
}

package com.centralvet.core.entities.repositories;

import com.centralvet.core.entities.Customer;
import com.centralvet.core.entities.Pet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends CrudRepository<Pet, Long> {

    List<Pet> findAllByCustomer(Customer customer);
}


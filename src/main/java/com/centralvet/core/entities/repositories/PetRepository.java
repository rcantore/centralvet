package com.centralvet.core.entities.repositories;

import com.centralvet.core.entities.Customer;
import com.centralvet.core.entities.Pet;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends CrudRepository<Pet, Long> {

    List<Pet> findAllByCustomer(Customer customer);

    Page<Pet> findAll(Example example, Pageable pageable);

    Page<Pet> findAllByCustomer(Customer customer, Example example, Pageable pageable);

}


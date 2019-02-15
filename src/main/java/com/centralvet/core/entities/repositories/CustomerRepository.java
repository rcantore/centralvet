package com.centralvet.core.entities.repositories;

import com.centralvet.core.entities.Clinic;
import com.centralvet.core.entities.Customer;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

    List<Customer> findAllByClinic(Clinic clinic);

    Page<Customer> findAll(Example example, Pageable pageable);

}

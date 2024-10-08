package com.centralvet.core.entities.repositories;

import com.centralvet.core.entities.Pet;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends CrudRepository<Pet, Long> {

    Page<Pet> findAll(Example example, Pageable pageable);

}


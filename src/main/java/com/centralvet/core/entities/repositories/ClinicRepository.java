package com.centralvet.core.entities.repositories;

import com.centralvet.core.entities.Clinic;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClinicRepository extends CrudRepository<Clinic, Long> {
    List<Clinic> findAll(Pageable pageable);

    List<Clinic> findAll(Example example, Pageable pageable);
}

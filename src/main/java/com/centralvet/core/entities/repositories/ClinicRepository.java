package com.centralvet.core.entities.repositories;

import com.centralvet.core.entities.Clinic;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClinicRepository extends CrudRepository<Clinic, Long> {
}

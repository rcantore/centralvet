package com.centralvet.jback.repository;

import com.centralvet.jback.model.Veterinaria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VeterinariaRepository extends JpaRepository<Veterinaria, Long> {
}

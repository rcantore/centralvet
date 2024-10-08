package com.centralvet.jback.repository;

import com.centralvet.jback.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    List<Cliente> findByVeterinariaId(Long veterinariaId);
}

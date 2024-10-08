package com.centralvet.jback.repository;

import com.centralvet.jback.model.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MascotaRepository extends JpaRepository<Mascota, Long> {
    List<Mascota> findByClienteId(Long clienteId);
    List<Mascota> findByClienteIdAndNombreContaining(Long clienteId, String nombre);
    
}

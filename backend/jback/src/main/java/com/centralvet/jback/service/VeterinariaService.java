package com.centralvet.jback.service;

import com.centralvet.jback.model.Veterinaria;
import com.centralvet.jback.model.Cliente;
import com.centralvet.jback.repository.VeterinariaRepository;
import com.centralvet.jback.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VeterinariaService {

    private final VeterinariaRepository veterinariaRepository;
    private final ClienteRepository clienteRepository;

    public VeterinariaService(VeterinariaRepository veterinariaRepository, ClienteRepository clienteRepository) {
        this.veterinariaRepository = veterinariaRepository;
        this.clienteRepository = clienteRepository;
    }

    public List<Veterinaria> getAllVeterinarias() {
        return veterinariaRepository.findAll();
    }

    public Veterinaria createVeterinaria(Veterinaria veterinaria) {
        return veterinariaRepository.save(veterinaria);
    }

    public List<Cliente> getClientesByVeterinaria(Long veterinariaId) {
        return clienteRepository.findByVeterinariaId(veterinariaId);
    }

    public Cliente addClienteToVeterinaria(Long veterinariaId, Cliente cliente) {
        Veterinaria veterinaria = veterinariaRepository.findById(veterinariaId)
            .orElseThrow(() -> new RuntimeException("Veterinaria not found"));
        cliente.setVeterinaria(veterinaria);
        return clienteRepository.save(cliente);
    }
}

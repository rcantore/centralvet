package com.centralvet.jback.service;

import com.centralvet.jback.model.Cliente;
import com.centralvet.jback.model.Mascota;
import com.centralvet.jback.repository.ClienteRepository;
import com.centralvet.jback.repository.MascotaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final MascotaRepository mascotaRepository;

    public ClienteService(ClienteRepository clienteRepository, MascotaRepository mascotaRepository) {
        this.clienteRepository = clienteRepository;
        this.mascotaRepository = mascotaRepository;
    }

    public List<Cliente> getAllClientes() {
        return clienteRepository.findAll();
    }

    public List<Mascota> getMascotasByCliente(Long clienteId) {
        return mascotaRepository.findByClienteId(clienteId);
    }

    public Cliente createCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }
}

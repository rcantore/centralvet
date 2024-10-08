package com.centralvet.jback.service;

import com.centralvet.jback.model.Mascota;
import com.centralvet.jback.model.Cliente;
import com.centralvet.jback.repository.MascotaRepository;
import com.centralvet.jback.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MascotaService {

    private final MascotaRepository mascotaRepository;
    private final ClienteRepository clienteRepository;

    public MascotaService(MascotaRepository mascotaRepository, ClienteRepository clienteRepository) {
        this.mascotaRepository = mascotaRepository;
        this.clienteRepository = clienteRepository;
    }

    public Mascota addMascotaToCliente(Long clienteId, Mascota mascota) {
        Cliente cliente = clienteRepository.findById(clienteId)
            .orElseThrow(() -> new RuntimeException("Cliente not found"));
        mascota.setCliente(cliente);
        return mascotaRepository.save(mascota);
    }

    public List<Mascota> getMascotasByClienteAndName(Long clienteId, String name) {
        if (name != null && !name.isEmpty()) {
            return mascotaRepository.findByClienteIdAndNombreContaining(clienteId, name);
        } else {
            return mascotaRepository.findByClienteId(clienteId);
        }
    }
}

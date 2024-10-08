package com.centralvet.jback.controller;

import com.centralvet.jback.model.Cliente;
import com.centralvet.jback.model.Mascota;
import com.centralvet.jback.service.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/clientes")
    public ResponseEntity<List<Cliente>> getAllClientes() {
        List<Cliente> clientes = clienteService.getAllClientes();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/cliente/{id}/mascotas")
    public ResponseEntity<List<Mascota>> getMascotasByCliente(@PathVariable Long id) {
        List<Mascota> mascotas = clienteService.getMascotasByCliente(id);
        return ResponseEntity.ok(mascotas);
    }
}

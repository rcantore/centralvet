package com.centralvet.jback.controller;

import com.centralvet.jback.model.Cliente;
import com.centralvet.jback.model.Mascota;
import com.centralvet.jback.service.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.Operation; 
import java.util.List;

@RestController
@RequestMapping("/api")
@OpenAPIDefinition(info = @Info(title = "CentralVet API", version = "v0.0.1"))
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @Operation(summary = "Obtener todos los clientes")
    @GetMapping("/clientes")
    public ResponseEntity<List<Cliente>> getAllClientes() {
        List<Cliente> clientes = clienteService.getAllClientes();
        return ResponseEntity.ok(clientes);
    }

    @Operation(summary = "Obtener todas las mascotas de un cliente")
    @GetMapping("/cliente/{id}/mascotas")
    public ResponseEntity<ﬂList<Mascota>> getMascotasByCliente(@PathVariable Long id) {
        List<Mascota> mascotas = clienteService.getMascotasByCliente(id);
        return ResponseEntity.ok(mascotas);
    }

    @Operation(summary = "Crear un nuevo cliente")
    @PostMapping("/cliente")
    public ResponseEntity<Cliente> createCliente(@RequestBody Cliente cliente) {
        Cliente createdCliente = clienteService.createCliente(cliente);
        return ResponseEntity.ok(createdCliente);
    }
    
}

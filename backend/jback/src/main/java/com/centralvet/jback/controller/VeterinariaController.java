package com.centralvet.jback.controller;

import com.centralvet.jback.model.Veterinaria;
import com.centralvet.jback.model.Cliente;
import com.centralvet.jback.service.VeterinariaService;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@OpenAPIDefinition(info = @Info(title = "CentralVet API", version = "v0.0.1"))
public class VeterinariaController {

    private final VeterinariaService veterinariaService;

    public VeterinariaController(VeterinariaService veterinariaService) {
        this.veterinariaService = veterinariaService;
    }

    @Operation(summary = "Obtener todas las veterinarias")
    @GetMapping("/veterinarias")
    public ResponseEntity<List<Veterinaria>> getAllVeterinarias() {
        List<Veterinaria> veterinarias = veterinariaService.getAllVeterinarias();
        return ResponseEntity.ok(veterinarias);
    }

    @Operation(summary = "Crear una nueva veterinaria")
    @PostMapping("/veterinaria")
    public ResponseEntity<Veterinaria> createVeterinaria(@RequestBody Veterinaria veterinaria) {
        Veterinaria createdVeterinaria = veterinariaService.createVeterinaria(veterinaria);
        return ResponseEntity.ok(createdVeterinaria);
    }

    @Operation(summary = "Obtener todos los clientes de una veterinaria")
    @GetMapping("/veterinaria/{id}/clientes")
    public ResponseEntity<List<Cliente>> getClientesByVeterinaria(@PathVariable Long id) {
        List<Cliente> clientes = veterinariaService.getClientesByVeterinaria(id);
        return ResponseEntity.ok(clientes);
    }

    @Operation(summary = "Agregar un cliente a una veterinaria")
    @PostMapping("/veterinaria/{id}/cliente")
    public ResponseEntity<Cliente> addClienteToVeterinaria(@PathVariable Long id, @RequestBody Cliente cliente) {
        Cliente addedCliente = veterinariaService.addClienteToVeterinaria(id, cliente);
        return ResponseEntity.ok(addedCliente);
    }
}

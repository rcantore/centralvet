package com.centralvet.jback.controller;

import com.centralvet.jback.model.Veterinaria;
import com.centralvet.jback.model.Cliente;
import com.centralvet.jback.service.VeterinariaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class VeterinariaController {

    private final VeterinariaService veterinariaService;

    public VeterinariaController(VeterinariaService veterinariaService) {
        this.veterinariaService = veterinariaService;
    }

    @GetMapping("/veterinarias")
    public ResponseEntity<List<Veterinaria>> getAllVeterinarias() {
        List<Veterinaria> veterinarias = veterinariaService.getAllVeterinarias();
        return ResponseEntity.ok(veterinarias);
    }

    @PostMapping("/veterinaria")
    public ResponseEntity<Veterinaria> createVeterinaria(@RequestBody Veterinaria veterinaria) {
        Veterinaria createdVeterinaria = veterinariaService.createVeterinaria(veterinaria);
        return ResponseEntity.ok(createdVeterinaria);
    }

    @GetMapping("/veterinaria/{id}/clientes")
    public ResponseEntity<List<Cliente>> getClientesByVeterinaria(@PathVariable Long id) {
        List<Cliente> clientes = veterinariaService.getClientesByVeterinaria(id);
        return ResponseEntity.ok(clientes);
    }

    @PostMapping("/veterinaria/{id}/cliente")
    public ResponseEntity<Cliente> addClienteToVeterinaria(@PathVariable Long id, @RequestBody Cliente cliente) {
        Cliente addedCliente = veterinariaService.addClienteToVeterinaria(id, cliente);
        return ResponseEntity.ok(addedCliente);
    }
}

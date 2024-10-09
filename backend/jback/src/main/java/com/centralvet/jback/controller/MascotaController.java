package com.centralvet.jback.controller;

import com.centralvet.jback.model.Mascota;
import com.centralvet.jback.service.MascotaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.OpenAPIDefinition; 
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.Operation; 
import java.util.List;

@RestController 
@RequestMapping("/api")
@OpenAPIDefinition(info = @Info(title = "CentralVet API", version = "v0.0.1"))
public class MascotaController {

    private final MascotaService mascotaService;

    public MascotaController(MascotaService mascotaService) {
        this.mascotaService = mascotaService;
    }

    @Operation(summary = "Agregar una mascota a un cliente")
    @PostMapping("/cliente/{id}/mascota")
    public ResponseEntity<Mascota> addMascotaToCliente(@PathVariable Long id, @RequestBody Mascota mascota) {
        Mascota addedMascota = mascotaService.addMascotaToCliente(id, mascota);
        return ResponseEntity.ok(addedMascota);
    }

    @Operation(summary = "Obtener todas las mascotas de un cliente")
    @GetMapping("/customers/{id}/pets")
    public ResponseEntity<List<Mascota>> getMascotasByClienteAndName(
            @PathVariable Long id,
            @RequestParam(required = false) String name) {
        List<Mascota> mascotas = mascotaService.getMascotasByClienteAndName(id, name);
        return ResponseEntity.ok(mascotas);
    }
}
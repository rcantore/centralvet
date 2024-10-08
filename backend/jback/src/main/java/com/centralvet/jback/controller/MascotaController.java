package com.centralvet.jback.controller;

import com.centralvet.jback.model.Mascota;
import com.centralvet.jback.service.MascotaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MascotaController {

    private final MascotaService mascotaService;

    public MascotaController(MascotaService mascotaService) {
        this.mascotaService = mascotaService;
    }

    @PostMapping("/cliente/{id}/mascota")
    public ResponseEntity<Mascota> addMascotaToCliente(@PathVariable Long id, @RequestBody Mascota mascota) {
        Mascota addedMascota = mascotaService.addMascotaToCliente(id, mascota);
        return ResponseEntity.ok(addedMascota);
    }

    @GetMapping("/customers/{id}/pets")
    public ResponseEntity<List<Mascota>> getMascotasByClienteAndName(
            @PathVariable Long id,
            @RequestParam(required = false) String name) {
        List<Mascota> mascotas = mascotaService.getMascotasByClienteAndName(id, name);
        return ResponseEntity.ok(mascotas);
    }
}
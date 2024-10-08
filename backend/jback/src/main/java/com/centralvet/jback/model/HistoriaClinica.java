package com.centralvet.jback.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class HistoriaClinica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "mascota_id")
    private Mascota mascota;

    @ManyToOne
    @JoinColumn(name = "veterinaria_id")
    private Veterinaria veterinaria;

    @OneToMany(mappedBy = "historiaClinica", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Consulta> consultas = new ArrayList<>();

    // Método para agregar una nueva consulta
    public void agregarConsulta(LocalDate fecha, String motivo, String diagnostico, String tratamiento) {
        Consulta consulta = new Consulta();
        consulta.setFecha(fecha);
        consulta.setMotivo(motivo);
        consulta.setDiagnostico(diagnostico);
        consulta.setTratamiento(tratamiento);
        consulta.setHistoriaClinica(this);
        consultas.add(consulta);
    }

}

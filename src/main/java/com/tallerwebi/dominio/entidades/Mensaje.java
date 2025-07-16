package com.tallerwebi.dominio.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Mensaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;
    private Long emisorId;
    private String nombreUsuario;
    private LocalDateTime fecha;

    public Mensaje() {
    }

    public Mensaje(String message, Long emisorId, String nombreUsuario, LocalDateTime fecha) {
        this.message = message;
        this.emisorId = emisorId;
        this.nombreUsuario = nombreUsuario;
        this.fecha = fecha;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getEmisorId() {
        return emisorId;
    }

    public void setEmisorId(Long emisorId) {
        this.emisorId = emisorId;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
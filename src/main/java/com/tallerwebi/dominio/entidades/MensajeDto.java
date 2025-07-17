package com.tallerwebi.dominio.entidades;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class MensajeDto {

    private Long id;
    private String texto;
    private Long emisorId;
    private String nombreUsuario;
    private String hora;

    public MensajeDto() {}

    public MensajeDto(String texto, Long emisorId, String nombreUsuario, LocalDateTime fecha) {
        this.texto = texto;
        this.emisorId = emisorId;
        this.nombreUsuario = nombreUsuario;
        this.hora = fecha.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("America/Argentina/Buenos_Aires")).format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
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
}

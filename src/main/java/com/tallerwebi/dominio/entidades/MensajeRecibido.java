package com.tallerwebi.dominio.entidades;

public class MensajeRecibido {

    private String message;
    private Long emisorId;
    private String nombreUsuario;

    public MensajeRecibido() {
    }

    public MensajeRecibido(String message, Long emisorId, String nombreUsuario) {
        this.message = message;
        this.emisorId = emisorId;
        this.nombreUsuario = nombreUsuario;
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
}
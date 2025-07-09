package com.tallerwebi.dominio.entidades;

public class MensajeDto {

    private String texto;
    private Long emisorId;
    private String nombreUsuario;

    public MensajeDto() {}

    public MensajeDto(String texto, Long emisorId, String nombreUsuario) {
        this.texto = texto;
        this.emisorId = emisorId;
        this.nombreUsuario = nombreUsuario;
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

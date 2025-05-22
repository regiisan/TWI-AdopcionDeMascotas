package com.tallerwebi.dominio;

public class SolicitudAdopcion {
    private String nombre;
    private String email;
    private String tipoVivienda;
    private String espacioDisponible;
    private boolean otrosAnimales;
    private String experiencia;
    private Long mascotaId;
    private String estado;

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTipoVivienda() { return tipoVivienda; }
    public void setTipoVivienda(String tipoVivienda) { this.tipoVivienda = tipoVivienda; }

    public String getEspacioDisponible() { return espacioDisponible; }
    public void setEspacioDisponible(String espacioDisponible) { this.espacioDisponible = espacioDisponible; }

    public boolean isOtrosAnimales() { return otrosAnimales; }
    public void setOtrosAnimales(boolean otrosAnimales) { this.otrosAnimales = otrosAnimales; }

    public String getExperiencia() { return experiencia; }
    public void setExperiencia(String experiencia) { this.experiencia = experiencia; }

    public Long getMascotaId() { return mascotaId; }
    public void setMascotaId(Long mascotaId) { this.mascotaId = mascotaId; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}





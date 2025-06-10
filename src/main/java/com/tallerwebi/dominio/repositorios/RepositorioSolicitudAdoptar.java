package com.tallerwebi.dominio.repositorios;

import com.tallerwebi.dominio.entidades.SolicitudAdopcion;

public interface RepositorioSolicitudAdoptar {
    SolicitudAdopcion buscarSolicitudPorId(Long id);
    void guardar(SolicitudAdopcion solicitud);
}

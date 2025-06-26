package com.tallerwebi.dominio.repositorios;

import com.tallerwebi.dominio.entidades.SolicitudAdopcion;

import java.util.List;

public interface RepositorioSolicitudAdoptar {
    SolicitudAdopcion buscarSolicitudPorId(Long id);
    void guardar(SolicitudAdopcion solicitud);
    List<SolicitudAdopcion> listarSolicitudes();
    void modificar(SolicitudAdopcion solicitud);
}

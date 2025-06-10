package com.tallerwebi.dominio.servicios;

import com.tallerwebi.dominio.entidades.SolicitudAdopcion;

import javax.transaction.Transactional;

@Transactional
public interface ServicioSolicitudAdoptar {
    void guardar(SolicitudAdopcion solicitud);
    SolicitudAdopcion buscarPorId(Long id);
}

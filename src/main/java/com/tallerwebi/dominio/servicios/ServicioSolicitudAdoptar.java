package com.tallerwebi.dominio.servicios;

import com.tallerwebi.dominio.entidades.SolicitudAdopcion;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface ServicioSolicitudAdoptar {
    void guardar(SolicitudAdopcion solicitud);
    SolicitudAdopcion buscarPorId(Long id);
    List<SolicitudAdopcion> obtenerSolicitudes();
    List<SolicitudAdopcion> obtenerSolicitudesPorEstado(String estado);
    void aprobarSolicitud(Long id);
    void rechazarSolicitud(Long id);
    int contarSolicitudesPendientes();
    int contarSolicitudesAprobadas();
}

package com.tallerwebi.infraestructura;


import com.tallerwebi.dominio.repositorios.RepositorioSolicitud;
import com.tallerwebi.dominio.entidades.SolicitudAdopcion;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RepositorioSolicitudImpl implements RepositorioSolicitud {

    private final List<SolicitudAdopcion> solicitudes = new ArrayList<>();

    public List<SolicitudAdopcion> obtenerTodas() {
        return solicitudes;
    }

    @Override
    public void guardar(SolicitudAdopcion solicitud) {

    }
}


package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.SolicitudAdopcion;
import com.tallerwebi.dominio.repositorios.RepositorioSolicitudAdoptar;
import com.tallerwebi.dominio.servicios.ServicioSolicitudAdoptar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("servicioSolicitudAdoptar")
public class ServicioSolicitudAdoptarImpl implements ServicioSolicitudAdoptar {

    private RepositorioSolicitudAdoptar repositorioSolicitudAdoptar;

    @Autowired
    public ServicioSolicitudAdoptarImpl(final RepositorioSolicitudAdoptar repositorioSolicitudAdoptar) {
        this.repositorioSolicitudAdoptar = repositorioSolicitudAdoptar;
    }

    @Override
    public void guardar(SolicitudAdopcion solicitud) {
        repositorioSolicitudAdoptar.guardar(solicitud);
    }

    @Override
    public SolicitudAdopcion buscarPorId(Long id) {
        return repositorioSolicitudAdoptar.buscarSolicitudPorId(id);
    }

    @Override
    public List<SolicitudAdopcion> obtenerSolicitudes() {
        return repositorioSolicitudAdoptar.listarSolicitudes();
    }

    @Override
    public List<SolicitudAdopcion> obtenerSolicitudesPorEstado(String estado) {
        return repositorioSolicitudAdoptar.listarSolicitudesPorEstado(estado);
    }


    @Override
    public void aprobarSolicitud(Long id) {
        SolicitudAdopcion solicitud = buscarPorId(id);
        if (solicitud != null) {
            solicitud.setEstado("Aprobada");
            repositorioSolicitudAdoptar.modificar(solicitud);
        }
    }

    @Override
    public void rechazarSolicitud(Long id) {
        SolicitudAdopcion solicitud = buscarPorId(id);
        if (solicitud != null) {
            solicitud.setEstado("Rechazada");
            repositorioSolicitudAdoptar.modificar(solicitud);
        }
    }
}

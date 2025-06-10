package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.SolicitudAdopcion;
import com.tallerwebi.dominio.repositorios.RepositorioSolicitudAdoptar;
import com.tallerwebi.dominio.servicios.ServicioSolicitudAdoptar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}

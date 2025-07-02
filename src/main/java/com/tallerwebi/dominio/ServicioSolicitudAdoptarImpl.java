package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.SolicitudAdopcion;
import com.tallerwebi.dominio.repositorios.RepositorioSolicitudAdoptar;
<<<<<<< HEAD
import com.tallerwebi.dominio.servicios.ServicioMail;
import com.tallerwebi.dominio.servicios.ServicioSolicitudAdoptar;

import jakarta.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

=======
import com.tallerwebi.dominio.servicios.ServicioSolicitudAdoptar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

>>>>>>> e74298ed02e547e84712e47f62922004fb5db00e
@Service("servicioSolicitudAdoptar")
public class ServicioSolicitudAdoptarImpl implements ServicioSolicitudAdoptar {

    private RepositorioSolicitudAdoptar repositorioSolicitudAdoptar;

    @Autowired
    public ServicioSolicitudAdoptarImpl(final RepositorioSolicitudAdoptar repositorioSolicitudAdoptar) {
        this.repositorioSolicitudAdoptar = repositorioSolicitudAdoptar;
    }

<<<<<<< HEAD
    @Autowired
    private ServicioMail servicioMail;


    @Override
    public void guardar(SolicitudAdopcion solicitud) {
        repositorioSolicitudAdoptar.guardar(solicitud);
        if (solicitud.getNombre() != null && solicitud.getEmail() != null) {
    try {
        servicioMail.enviarMail(
            solicitud.getEmail(),
            "Tu solicitud de adopción fue recibida",
            "Gracias por tu interés en adoptar."+"\nVamos a revisar tu solicitud y te contactaremos pronto."
        );
    } catch (MessagingException e) {
        e.printStackTrace();
    }
}

=======
    @Override
    public void guardar(SolicitudAdopcion solicitud) {
        repositorioSolicitudAdoptar.guardar(solicitud);
>>>>>>> e74298ed02e547e84712e47f62922004fb5db00e
    }

    @Override
    public SolicitudAdopcion buscarPorId(Long id) {
        return repositorioSolicitudAdoptar.buscarSolicitudPorId(id);
    }
<<<<<<< HEAD
=======

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
>>>>>>> e74298ed02e547e84712e47f62922004fb5db00e
}

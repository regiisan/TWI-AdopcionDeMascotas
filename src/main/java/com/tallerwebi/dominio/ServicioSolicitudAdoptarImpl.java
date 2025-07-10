package com.tallerwebi.dominio;

import com.tallerwebi.dominio.entidades.SolicitudAdopcion;
import com.tallerwebi.dominio.repositorios.RepositorioSolicitudAdoptar;
import com.tallerwebi.dominio.servicios.ServicioMail;
import com.tallerwebi.dominio.servicios.ServicioSolicitudAdoptar;

import jakarta.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("servicioSolicitudAdoptar")
public class ServicioSolicitudAdoptarImpl implements ServicioSolicitudAdoptar {

    private RepositorioSolicitudAdoptar repositorioSolicitudAdoptar;

    @Autowired
    public ServicioSolicitudAdoptarImpl(final RepositorioSolicitudAdoptar repositorioSolicitudAdoptar) {
        this.repositorioSolicitudAdoptar = repositorioSolicitudAdoptar;
        this.servicioMail = servicioMail;
    }

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
                        "Gracias por tu interés en adoptar." + "\nVamos a revisar tu solicitud y te contactaremos pronto."
                );
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
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

        if (solicitud.getEmail() != null) {
            try {
                servicioMail.enviarMail(
                    solicitud.getEmail(),
                    "Tu solicitud fue aprobada",
                    "¡Felicitaciones! Tu solicitud N°" + solicitud.getId() + " fue aprobada."
                );
            } catch (MessagingException e) {
                System.err.println("Error al enviar mail: " + e.getMessage());
            }
        }
    }
}

@Override
public void rechazarSolicitud(Long id) {
    SolicitudAdopcion solicitud = buscarPorId(id);
    if (solicitud != null) {
        solicitud.setEstado("Rechazada");
        repositorioSolicitudAdoptar.modificar(solicitud);

        if (solicitud.getEmail() != null) {
            try {
                servicioMail.enviarMail(
                    solicitud.getEmail(),
                    "Tu solicitud fue rechazada",
                    "Lamentablemente, tu solicitud N°" + solicitud.getId() + " fue rechazada."
                );
            } catch (MessagingException e) {
                System.err.println("Error al enviar mail: " + e.getMessage());
            }
        }
    }
}
    @Override
    public int contarSolicitudesPendientes() {
        return repositorioSolicitudAdoptar.contarSolicitudesPendientes();
    }

    @Override
    public int contarSolicitudesAprobadas() {
        return repositorioSolicitudAdoptar.contarSolicitudesAprobadas();
    }

}

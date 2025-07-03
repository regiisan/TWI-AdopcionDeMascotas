package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.entidades.SolicitudAdopcion;
import com.tallerwebi.dominio.servicios.ServicioMascota;
import com.tallerwebi.dominio.servicios.ServicioSolicitudAdoptar;
import com.tallerwebi.dominio.servicios.ServicioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ControladorAdmin {

    private ServicioSolicitudAdoptar servicioSolicitudAdoptar;
    private ServicioMascota servicioMascota;
    private ServicioUsuario servicioUsuario;

    @Autowired
    public ControladorAdmin(ServicioSolicitudAdoptar servicioSolicitudAdoptar, ServicioMascota servicioMascota, ServicioUsuario servicioUsuario) {
        this.servicioSolicitudAdoptar = servicioSolicitudAdoptar;
        this.servicioMascota = servicioMascota;
        this.servicioUsuario = servicioUsuario;
    }

    @RequestMapping(path = "/admin/solicitudes", method = RequestMethod.GET)
    public ModelAndView mostrarSolicitudes(@RequestParam(required = false) String estado, HttpSession session) {
        String rol = (String) session.getAttribute("ROL");
        ModelAndView model;

        if (rol != null && rol.equalsIgnoreCase("ADMIN")) {

            List<SolicitudAdopcion> solicitudes;

            if (estado != null && !estado.isEmpty()) {
                solicitudes = servicioSolicitudAdoptar.obtenerSolicitudesPorEstado(estado);
            } else {
                solicitudes = servicioSolicitudAdoptar.obtenerSolicitudes();

            }

            model = new ModelAndView("solicitudesDeAdopcion");
            model.addObject("solicitudes", solicitudes);
            model.addObject("estadoSeleccionado", estado);
            return model;

        } else {
            model = new ModelAndView("redirect:/home");
        }

        return model;
    }

    @RequestMapping(path = "/admin/solicitudes/{id}/aprobar", method = RequestMethod.POST)
    public ModelAndView aprobarSolicitud(@PathVariable Long id, HttpSession session) {
        String rol = (String) session.getAttribute("ROL");

        if (rol != null && rol.equalsIgnoreCase("ADMIN")) {
            servicioSolicitudAdoptar.aprobarSolicitud(id);
        }

        return new ModelAndView("redirect:/admin/solicitudes");
    }

    @RequestMapping(path = "/admin/solicitudes/{id}/rechazar", method = RequestMethod.POST)
    public ModelAndView rechazarSolicitud(@PathVariable Long id, HttpSession session) {
        String rol = (String) session.getAttribute("ROL");

        if (rol != null && rol.equalsIgnoreCase("ADMIN")) {
            servicioSolicitudAdoptar.rechazarSolicitud(id);
        }

        return new ModelAndView("redirect:/admin/solicitudes");
    }

    @RequestMapping(path = "/admin/home", method = RequestMethod.GET)
    public ModelAndView mostrarEstadisticas(HttpSession session) {
        String rol = (String) session.getAttribute("ROL");

        if (rol == null || !rol.equalsIgnoreCase("ADMIN")) {
            return new ModelAndView("redirect:/home");
        }

        int solicitudesPendientes = servicioSolicitudAdoptar.contarSolicitudesPendientes();
        int mascotasPendientes = servicioMascota.contarMascotasPendientes();
        int adopcionesExitosas = servicioSolicitudAdoptar.contarSolicitudesAprobadas();
        int usuariosActivos = servicioUsuario.contarUsuariosActivos();

        ModelAndView model = new ModelAndView("homeAdmin");
        model.addObject("solicitudesPendientes", solicitudesPendientes);
        model.addObject("mascotasPendientes", mascotasPendientes);
        model.addObject("adopcionesExitosas", adopcionesExitosas);
        model.addObject("usuariosActivos", usuariosActivos);

        return model;
    }
}

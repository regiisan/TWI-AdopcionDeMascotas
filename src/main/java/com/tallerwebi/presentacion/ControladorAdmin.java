package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.entidades.SolicitudAdopcion;
import com.tallerwebi.dominio.servicios.ServicioSolicitudAdoptar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ControladorAdmin {

    private ServicioSolicitudAdoptar servicioSolicitudAdoptar;

    @Autowired
    public ControladorAdmin(ServicioSolicitudAdoptar servicioSolicitudAdoptar) {
        this.servicioSolicitudAdoptar = servicioSolicitudAdoptar;
    }

    @RequestMapping(path = "/admin/solicitudes", method = RequestMethod.GET)
    public ModelAndView mostrarSolicitudes(@RequestParam(required = false) String estado, HttpSession session) {
        String rol = (String) session.getAttribute("ROL");
        ModelAndView model;

        if (rol != null && rol.equalsIgnoreCase("ADMIN")) {
            List<SolicitudAdopcion> solicitudes = servicioSolicitudAdoptar.obtenerSolicitudes();
            
            if (estado != null && !estado.isEmpty()) {
                solicitudes = solicitudes.stream()
                        .filter(s -> s.getEstado().equalsIgnoreCase(estado))
                        .collect(Collectors.toList());
            }
            
            model = new ModelAndView("solicitudesDeAdopcion");
            model.addObject("solicitudes", solicitudes);
            model.addObject("filtroEstado", estado);
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
}

package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.entidades.SolicitudAdopcion;
import com.tallerwebi.dominio.servicios.ServicioSolicitudAdoptar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ControladorAdmin {

    private ServicioSolicitudAdoptar servicioSolicitudAdoptar;

    @Autowired
    public ControladorAdmin(ServicioSolicitudAdoptar servicioSolicitudAdoptar) {
        this.servicioSolicitudAdoptar = servicioSolicitudAdoptar;
    }

    @RequestMapping(path = "/admin/solicitudes", method = RequestMethod.GET)
    public ModelAndView mostrarSolicitudes(HttpSession session) {
        String rol = (String) session.getAttribute("ROL");
        ModelAndView model;

        if (rol != null && rol.equalsIgnoreCase("ADMIN")) {
            List<SolicitudAdopcion> solicitudes = servicioSolicitudAdoptar.obtenerSolicitudes();
            model = new ModelAndView("solicitudesDeAdopcion");
            model.addObject("solicitudes", solicitudes);
            return model;
        } else {
            model = new ModelAndView("redirect:/home");
        }
        return model;
    }
}

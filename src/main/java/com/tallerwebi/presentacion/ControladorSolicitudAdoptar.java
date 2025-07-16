package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.entidades.Mascota;
import com.tallerwebi.dominio.entidades.SolicitudAdopcion;
import com.tallerwebi.dominio.servicios.ServicioMascota;
import com.tallerwebi.dominio.servicios.ServicioSolicitudAdoptar;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;


@Controller
public class ControladorSolicitudAdoptar {

    private final ServicioMascota servicioMascota;
    private final ServicioSolicitudAdoptar servicioSolicitudAdoptar;

    public ControladorSolicitudAdoptar(ServicioMascota servicioMascota, ServicioSolicitudAdoptar servicioSolicitudAdoptar) {
        this.servicioMascota = servicioMascota;
        this.servicioSolicitudAdoptar = servicioSolicitudAdoptar;
    }

    @GetMapping("/mascota/{id}/adoptar")
    public ModelAndView mostrarFormularioAdopcion(@PathVariable Long id, HttpSession session) {
        Long idUsuario = (Long) session.getAttribute("idUsuario");

        if (idUsuario == null) {
            return new ModelAndView("redirect:/login");
        }

        SolicitudAdopcion solicitud = new SolicitudAdopcion();
        Mascota mascota = servicioMascota.obtenerMascotaPorId(id);
        solicitud.setMascota(mascota);

        ModelAndView model = new ModelAndView("formulario-adopcion");
        model.addObject("mascota", mascota);
        model.addObject("solicitud", solicitud);

        return model;
    }


    @PostMapping("/mascota/{id}/adoptar/guardar")
    public ModelAndView guardarSolicitudAdopcion(@ModelAttribute("solicitud") SolicitudAdopcion solicitud, @PathVariable Long id, RedirectAttributes redirectAttributes) {
        Mascota mascota = servicioMascota.obtenerMascotaPorId(id);
        ModelAndView model = new ModelAndView("redirect:/mascotas");

        if (mascota != null) {
            solicitud.setMascota(mascota);
            solicitud.setEstado("Pendiente");
            servicioSolicitudAdoptar.guardar(solicitud);
            redirectAttributes.addFlashAttribute("mensaje", "¡Tu formulario se envió correctamente! Te enviaremos un correo electrónico con tu número de solicitud y nos pondremos en contacto cuando resolvamos tu petición. ¡Gracias por formar parte de AdoPets!");
        }

        return model;
    }

}

